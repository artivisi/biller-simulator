package com.artivisi.ppob.simulator.gateway.pln;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

import javax.annotation.PostConstruct;

import org.apache.commons.lang.StringUtils;
import org.joda.time.format.DateTimeFormat;
import org.jpos.iso.BaseChannel;
import org.jpos.iso.ISOMsg;
import org.jpos.iso.ISORequestListener;
import org.jpos.iso.ISOServer;
import org.jpos.iso.ISOSource;
import org.jpos.util.Log4JListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.artivisi.ppob.simulator.entity.Pelanggan;
import com.artivisi.ppob.simulator.entity.TagihanPascabayar;
import com.artivisi.ppob.simulator.gateway.pln.constants.MTIConstants;
import com.artivisi.ppob.simulator.gateway.pln.constants.ResponseCode;
import com.artivisi.ppob.simulator.gateway.pln.jpos.PlnChannel;
import com.artivisi.ppob.simulator.gateway.pln.jpos.PlnPackager;
import com.artivisi.ppob.simulator.service.PpobSimulatorService;

public class PlnGateway implements ISORequestListener {
	
	private static final Logger logger = LoggerFactory.getLogger(PlnGateway.class);
	
	@Autowired private PpobSimulatorService ppobSimulatorService;
	
	private Integer port = 11111;

	public PlnGateway(Integer port) {
		this.port = port;
	}

	@PostConstruct
	public void init(){
		try {
			PlnChannel channel = new PlnChannel(new PlnPackager());
			ISOServer server = new ISOServer(port, channel, null);
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
			
			
		} catch (Exception err){
			logger.error(err.getMessage(), err);
		}
		return false;
	}

	private boolean handleInquiryRequest(ISOSource src, ISOMsg msg) throws Exception {
		ISOMsg response = (ISOMsg) msg.clone();
		response.setMTI(MTIConstants.INQUIRY_RESPONSE);
		
		String bit48Request = msg.getString(48); 
		
		if(bit48Request.length() != 19) {
			logger.error("[POSTPAID] - [INQ-REQ] - Invalid bit 48 [{}]", bit48Request);
			response.set(39, ResponseCode.ERROR_INVALID_MESSAGE);
			src.send(response);
			return true;
		}
		
		String idpel = bit48Request.substring(7);
		Pelanggan p = ppobSimulatorService.findPelangganByIdpel(idpel);
		if(p == null) {
			logger.error("[POSTPAID] - [INQ-REQ] - IDPEL tidak ditemukan [{}]", idpel);
			response.set(39, ResponseCode.ERROR_UNKNOWN_SUBSCRIBER);
			src.send(response);
			return true;
		}
		
		List<TagihanPascabayar> daftarTagihan = ppobSimulatorService.findTagihan(p);
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
		
		StringBuffer bit48Response = createBit48InquiryPostpaidResponse(bit48Request, p, daftarTagihan, tagihanDikirim);
		
		response.set(39, ResponseCode.SUCCESSFUL);
		response.set(48, bit48Response.toString());
		
		src.send(response);
		return true;
	}

	private StringBuffer createBit48InquiryPostpaidResponse(
			String bit48Request, Pelanggan p,
			List<TagihanPascabayar> daftarTagihan,
			List<TagihanPascabayar> tagihanDikirim) {
		StringBuffer bit48Response = new StringBuffer();
		bit48Response.append(bit48Request);
		bit48Response.append(tagihanDikirim.size());
		bit48Response.append(StringUtils.leftPad(String.valueOf(daftarTagihan.size()), 2, "0"));
		bit48Response.append(tagihanDikirim.get(0).getId());
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
}
