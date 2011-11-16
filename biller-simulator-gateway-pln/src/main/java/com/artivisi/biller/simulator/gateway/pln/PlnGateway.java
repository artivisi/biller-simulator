/**
 * Copyright (C) 2011 ArtiVisi Intermedia <info@artivisi.com>
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.artivisi.biller.simulator.gateway.pln;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

import org.apache.commons.lang.StringUtils;
import org.joda.time.format.DateTimeFormat;
import org.jpos.iso.BaseChannel;
import org.jpos.iso.ISOException;
import org.jpos.iso.ISOFilter.VetoException;
import org.jpos.iso.ISOMsg;
import org.jpos.iso.ISORequestListener;
import org.jpos.iso.ISOServer;
import org.jpos.iso.ISOSource;
import org.jpos.util.Log4JListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.artivisi.biller.simulator.entity.Pelanggan;
import com.artivisi.biller.simulator.entity.PembayaranPascabayar;
import com.artivisi.biller.simulator.entity.TagihanPascabayar;
import com.artivisi.biller.simulator.gateway.pln.constants.MTIConstants;
import com.artivisi.biller.simulator.gateway.pln.constants.ResponseCode;
import com.artivisi.biller.simulator.gateway.pln.entity.InquiryPostpaidResponse;
import com.artivisi.biller.simulator.gateway.pln.entity.InquiryPostpaidResponseDetail;
import com.artivisi.biller.simulator.gateway.pln.jpos.PlnChannel;
import com.artivisi.biller.simulator.gateway.pln.jpos.PlnPackager;
import com.artivisi.biller.simulator.gateway.pln.service.PlnService;
import com.artivisi.biller.simulator.service.BillerSimulatorService;
import com.artivisi.biller.simulator.service.PlnSimulatorService;

public class PlnGateway implements ISORequestListener {
	
	private static final Logger logger = LoggerFactory.getLogger(PlnGateway.class);
	
	@Autowired private PlnSimulatorService plnSimulatorService;
	@Autowired private BillerSimulatorService billerSimulatorService;
	@Autowired private PlnService plnService;
	
	private Integer port = 11111;
	private ISOServer server;
	public PlnGateway(Integer port) {
		this.port = port;
	}

	@PostConstruct
	public void init(){
		try {
			PlnChannel channel = new PlnChannel(new PlnPackager());
			server = new ISOServer(port, channel, null);
			server.addISORequestListener(this);
			
			org.jpos.util.Logger jposLogger = new org.jpos.util.Logger();
		    Log4JListener log4JListener = new Log4JListener();
		    log4JListener.setLevel("info");
		    jposLogger.addListener(log4JListener);
		    server.setLogger(jposLogger, "pln-server");
		    channel.setLogger(jposLogger, "pln-channel");
		    
		    new Thread(server).start();
		    logger.info("Pln Gateway started at port [{}]", port);
		} catch (Exception err){
			logger.error(err.getMessage(), err);
		}
	}
	
	@PreDestroy
	public void shutdown(){
		try {
			server.shutdown();
		} catch (Exception err) {
			logger.error(err.getMessage(), err);
		}
	}

	@Override
	public boolean process(ISOSource src, ISOMsg msg) {
		try {
			logger.info("Incoming message from [{}]",((BaseChannel) src).getSocket().getInetAddress().getHostAddress());
			logger.info("Message Stream [{}]",new String(msg.pack()));
			
			if(MTIConstants.NETWORK_MANAGEMENT_REQUEST.equals(msg.getMTI())) {
				return handleNetworkManagementRequest(src, msg);
			}
			
			if(MTIConstants.INQUIRY_REQUEST.equals(msg.getMTI())) {
				return handleInquiryRequest(src, msg);
			}

			if(MTIConstants.PAYMENT_REQUEST.equals(msg.getMTI())) {
				return handlePaymentRequest(src, msg);
			}
			
			
		} catch (Exception err){
			logger.error(err.getMessage(), err);
		}
		return false;
	}

	private boolean handlePaymentRequest(ISOSource src, ISOMsg msg) throws Exception {
		ISOMsg response = (ISOMsg) msg.clone();
		response.setMTI(MTIConstants.PAYMENT_RESPONSE);
		
		String bank = msg.getString(32);
		if(billerSimulatorService.findBankByKode(bank) == null){
			logger.debug("[POSTPAID] - [PAY-REQ] - Bit 32 [{}]", bank);
			logger.error("[POSTPAID] - [PAY-REQ] - Invalid bit 32 [{}]", bank);
			response.set(39, ResponseCode.ERROR_UNREGISTERED_BANK_CODE);
			src.send(response);
			return true;
		}
		
		String pan = msg.getString(2);
		if(pan.length() != 5) {
			logger.error("[POSTPAID] - [PAY-REQ] - Invalid bit 2 [{}]", pan);
			response.set(39, ResponseCode.ERROR_INVALID_MESSAGE);
			src.send(response);
			return true;
		}
		
		String produk = pan.substring(2);
		if("501".equals(produk)) {
			return handlePaymentPostpaid(src, msg, response);
		} else {
			logger.error("[POSTPAID] - [PAY-REQ] - Invalid produk [{}]", produk);
			response.set(39, ResponseCode.ERROR_UNREGISTERED_PRODUCT);
			src.send(response);
			return true;
		}
	}

	private boolean handleInquiryRequest(ISOSource src, ISOMsg msg) throws Exception {
		ISOMsg response = (ISOMsg) msg.clone();
		response.setMTI(MTIConstants.INQUIRY_RESPONSE);
		
		String bank = msg.getString(32);
		if(billerSimulatorService.findBankByKode(bank) == null){
			logger.debug("[POSTPAID] - [INQ-REQ] - Bit 32 [{}]", bank);
			logger.error("[POSTPAID] - [INQ-REQ] - Invalid bit 32 [{}]", bank);
			response.set(39, ResponseCode.ERROR_UNREGISTERED_BANK_CODE);
			src.send(response);
			return true;
		}
		
		String pan = msg.getString(2);
		if(pan.length() != 5) {
			logger.error("[POSTPAID] - [INQ-REQ] - Invalid bit 2 [{}]", pan);
			response.set(39, ResponseCode.ERROR_INVALID_MESSAGE);
			src.send(response);
			return true;
		}
		
		String produk = pan.substring(2);
		if("501".equals(produk)) {
			return handleInquiryPostpaid(src, msg, response);
		} else {
			logger.error("[POSTPAID] - [INQ-REQ] - Invalid produk [{}]", produk);
			response.set(39, ResponseCode.ERROR_UNREGISTERED_PRODUCT);
			src.send(response);
			return true;
		}
		
	}
	
	private boolean handlePaymentPostpaid(ISOSource src, ISOMsg msg,ISOMsg response) throws Exception {
		String bit48Request = msg.getString(48); 
		if(bit48Request.length() < 55) {
			logger.error("[POSTPAID] - [PAY-REQ] - Invalid bit 48 [{}]", bit48Request);
			response.set(39, ResponseCode.ERROR_INVALID_MESSAGE);
			src.send(response);
			return true;
		}
		
		String plnRef = bit48Request.substring(23,55).toLowerCase();
		InquiryPostpaidResponse daftarTagihan = plnService.findInquiryPostpaidResponse(plnRef);
		
		if(daftarTagihan == null) {
			logger.error("[POSTPAID] - [PAY-REQ] - Invalid PLN REF [{}]", plnRef);
			response.set(39, ResponseCode.ERROR_PLN_REFNUM_NOT_VALID);
			src.send(response);
			return true;
		}
		
		String switcher = bit48Request.substring(0,7);
		Integer hold = 0;
		
		for (InquiryPostpaidResponseDetail detail : daftarTagihan.getDetails()) {
			PembayaranPascabayar payment = new PembayaranPascabayar();
			payment.setBank(msg.getString(32));
			payment.setLoket("");
			payment.setMerchantCategory(msg.getString(26));
			payment.setOperator("");
			payment.setSwitcher(switcher);
			payment.setTagihanPascabayar(detail.getTagihanPascabayar());
			plnSimulatorService.save(payment);
			
			hold = detail.getTagihanPascabayar().getPelanggan().getHoldResponse();
		}

		response.set(39, ResponseCode.SUCCESSFUL);
		
		try {
			logger.info("[POSTPAID] - [PAY-REQ] - Pelanggan diset untuk hold [{}]", hold);
			Thread.sleep(hold);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		
		src.send(response);
		return true;
	}

	private boolean handleInquiryPostpaid(ISOSource src, ISOMsg msg,ISOMsg response) throws ISOException, IOException, VetoException {
		String bit48Request = msg.getString(48); 
		if(bit48Request.length() != 19) {
			logger.error("[POSTPAID] - [INQ-REQ] - Invalid bit 48 [{}]", bit48Request);
			response.set(39, ResponseCode.ERROR_INVALID_MESSAGE);
			src.send(response);
			return true;
		}
		

		String mitra = bit48Request.substring(0,7);
		if(billerSimulatorService.findMitraByKode(mitra.trim()) == null){
			logger.debug("[POSTPAID] - [INQ-REQ] - Mitra [{}]", mitra);
			logger.error("[POSTPAID] - [INQ-REQ] - Kode mitra tidak ditemukan [{}]", mitra);
			response.set(39, ResponseCode.ERROR_UNREGISTERED_SWITCHING);
			src.send(response);
			return true;
		}
		
		String idpel = bit48Request.substring(7);
		Pelanggan p = plnSimulatorService.findPelangganByIdpel(idpel);
		if(p == null) {
			logger.error("[POSTPAID] - [INQ-REQ] - IDPEL tidak ditemukan [{}]", idpel);
			response.set(39, ResponseCode.ERROR_UNKNOWN_SUBSCRIBER);
			src.send(response);
			return true;
		}
		
		
		if(!ResponseCode.SUCCESSFUL.equals(p.getResponseCode())) {
			logger.error("[POSTPAID] - [INQ-REQ] - Pelanggan diset untuk RC [{}]", p.getResponseCode());
			response.set(39, p.getResponseCode());
			src.send(response);
			return true;
		}
		
		List<TagihanPascabayar> daftarTagihan = plnSimulatorService.findTagihan(p);
		if(daftarTagihan.size() < 1){
			logger.error("[POSTPAID] - [INQ-REQ] - Tagihan untuk idpel [{}] tidak ada", idpel);
			response.set(39, ResponseCode.ERROR_CURRENT_BILL_IS_NOT_AVAILABLE);
			src.send(response);
			return true;
		}
		
		List<TagihanPascabayar> tagihanDikirim;
		if(daftarTagihan.size() > 4) {
			tagihanDikirim = daftarTagihan.subList(0, 4);
		} else {
			tagihanDikirim = daftarTagihan;
		}
		
		BigDecimal amount = BigDecimal.ZERO;
		for (TagihanPascabayar tagihanPascabayar : tagihanDikirim) {
			amount = amount.add(tagihanPascabayar.getBill());
			amount = amount.add(tagihanPascabayar.getDenda());
		}
		
		response.set(4, "360"+"0"+StringUtils.leftPad(amount.setScale(0, RoundingMode.HALF_EVEN).toString(), 12, "0"));
		
		InquiryPostpaidResponse ipr = new InquiryPostpaidResponse();
		ipr.setBank(msg.getString(32));
		ipr.setSwitcher(mitra);
		ipr.setStan(msg.getString(11));
		
		for (TagihanPascabayar tagihanPascabayar : tagihanDikirim) {
			InquiryPostpaidResponseDetail detail = new InquiryPostpaidResponseDetail();
			detail.setTagihanPascabayar(tagihanPascabayar);
			detail.setInquiryPostpaidResponse(ipr);
			ipr.getDetails().add(detail);
		}
		
		plnService.save(ipr);
		
		StringBuffer bit48Response = createBit48InquiryPostpaidResponse(bit48Request, p, daftarTagihan, tagihanDikirim, ipr);
		
		response.set(39, ResponseCode.SUCCESSFUL);
		response.set(48, bit48Response.toString());
		
		try {
			logger.info("[POSTPAID] - [INQ-REQ] - Pelanggan diset untuk hold [{}]", p.getHoldResponse());
			Thread.sleep(p.getHoldResponse());
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		
		src.send(response);
		return true;
	}

	private StringBuffer createBit48InquiryPostpaidResponse(
			String bit48Request, Pelanggan p,
			List<TagihanPascabayar> daftarTagihan,
			List<TagihanPascabayar> tagihanDikirim, InquiryPostpaidResponse ipr) {
		StringBuffer bit48Response = new StringBuffer();
		bit48Response.append(bit48Request);
		bit48Response.append(tagihanDikirim.size());
		bit48Response.append(StringUtils.leftPad(String.valueOf(daftarTagihan.size()), 2, "0"));
		bit48Response.append(ipr.getId().toUpperCase());
		bit48Response.append(StringUtils.rightPad(p.getNama(), 25, " "));
		bit48Response.append(StringUtils.rightPad(p.getServiceUnit(), 5, " "));
		bit48Response.append(StringUtils.rightPad(p.getServiceUnitPhone(), 15, " "));
		bit48Response.append(StringUtils.rightPad(p.getSubscriberSegmentation(), 4, " "));
		bit48Response.append(StringUtils.leftPad(p.getPowerConsumingCategory(), 9, "0"));
		bit48Response.append(StringUtils.leftPad("", 9, "0")); // total admin charges
		
		for (TagihanPascabayar t : tagihanDikirim) {
			bit48Response.append(DateTimeFormat.forPattern("yyyyMM").print(t.getBillPeriod().getTime()));
			bit48Response.append(DateTimeFormat.forPattern("ddMMyyyy").print(t.getDueDate().getTime()));
			bit48Response.append(DateTimeFormat.forPattern("ddMMyyyy").print(t.getMeterReadDate().getTime()));
			bit48Response.append(StringUtils.leftPad(t.getBill().setScale(0, RoundingMode.HALF_EVEN).toString(), 11, "0"));
			if(BigDecimal.ZERO.compareTo(t.getInsentif()) > 0){
				bit48Response.append("D");
			} else {
				bit48Response.append("C");
			}
			bit48Response.append(StringUtils.leftPad(t.getInsentif().abs().setScale(0, RoundingMode.HALF_EVEN).toString(), 10, "0"));
			bit48Response.append(StringUtils.leftPad(t.getVat().setScale(0, RoundingMode.HALF_EVEN).toString(), 10, "0"));
			bit48Response.append(StringUtils.leftPad(t.getDenda().setScale(0, RoundingMode.HALF_EVEN).toString(), 10, "0"));
			bit48Response.append(StringUtils.leftPad(t.getPreviousMeterRead1(), 8, "0"));
			bit48Response.append(StringUtils.leftPad(t.getCurrentMeterRead1(), 8, "0"));
			bit48Response.append(StringUtils.leftPad(t.getPreviousMeterRead2(), 8, "0"));
			bit48Response.append(StringUtils.leftPad(t.getCurrentMeterRead2(), 8, "0"));
			bit48Response.append(StringUtils.leftPad(t.getPreviousMeterRead3(), 8, "0"));
			bit48Response.append(StringUtils.leftPad(t.getCurrentMeterRead3(), 8, "0"));
		}
		return bit48Response;
	}

	private boolean handleNetworkManagementRequest(ISOSource src, ISOMsg msg) throws Exception {
		ISOMsg response = (ISOMsg) msg.clone();
		response.setMTI(MTIConstants.NETWORK_MANAGEMENT_RESPONSE);
		response.set(39, ResponseCode.SUCCESSFUL);
		src.send(response);
		return true;
	}
	
	private boolean handleInquiryNontaglis(ISOSource src , ISOMsg msg, ISOMsg response) throws ISOException, IOException{
		String bit48Request = msg.getString(48);
		if(bit48Request.length() != 20){
			logger.error("[POSTPAID] - [INQ-REQ] - Invalid bit 48 [{}]", bit48Request);
			response.set(38 , ResponseCode.ERROR_INVALID_MESSAGE);
			src.send(response);
			return true ;
		}
		
		String mitra = bit48Request.substring(0,7);
		if (billerSimulatorService.findMitraByKode(mitra.trim()) == null){
			logger.debug("[POSTPAID] - [INQ-REQ] - Mitra [{}]", mitra);
			logger.error("[POSTPAID] - [INQ-REQ] - Kode mitra tidak ditemukan [{}]", mitra);
			response.set(39, ResponseCode.ERROR_UNREGISTERED_SWITCHING);
			src.send(response);
			return true;
		}
		
		String idpel = bit48Request.substring(7,39);
		Pelanggan p = plnSimulatorService.findPelangganByIdpel(idpel);
		if(p == null) {
			logger.error("[POSTPAID] - [INQ-REQ] - IDPEL tidak ditemukan [{}]", idpel);
			response.set(39, ResponseCode.ERROR_UNKNOWN_SUBSCRIBER);
			src.send(response);
			return true;
		}
		
		if(!ResponseCode.SUCCESSFUL.equals(p.getResponseCode())) {
			logger.error("[PREPAID] - [INQ-REQ] - Pelanggan diset untuk RC [{}]", p.getResponseCode());
			response.set(39, p.getResponseCode());
			src.send(response);
			return true;
		}
			
		return true;
	}
	
	private boolean handlePaymentNontaglis(String regnum, BigDecimal amount) throws ISOException{
		return true;
	}
}
