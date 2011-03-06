package com.artivisi.ppob.simulator.gateway.pln;

import static org.junit.Assert.assertEquals;

import java.util.Date;

import org.joda.time.format.DateTimeFormat;
import org.jpos.iso.ISOMsg;
import org.junit.Test;

import com.artivisi.ppob.simulator.gateway.pln.constants.MTIConstants;
import com.artivisi.ppob.simulator.gateway.pln.constants.ResponseCode;
import com.artivisi.ppob.simulator.gateway.pln.jpos.PlnChannel;

public class PaymentPostpaidTest extends BaseTest {
	
	@Test
	public void testPaymentNormal() throws Exception {
		String idpel = "123456789011";
		String switcher = "ARTIVIS";
		String bank = "BANKABC";
		
		ISOMsg inqRequest = new ISOMsg();
		inqRequest.setMTI(MTIConstants.INQUIRY_REQUEST);
		inqRequest.set(2, "51501");
		inqRequest.set(11, "123456789012");
		inqRequest.set(12, DateTimeFormat.forPattern("yyyyMMddHHmmss").print(new Date().getTime()));
		inqRequest.set(26, "6012");
		inqRequest.set(32, bank);
		inqRequest.set(48, switcher + idpel);
		
		PlnChannel channel = createChannel();
		channel.connect();
		channel.send(inqRequest);
		ISOMsg inqResponse = channel.receive();
		channel.disconnect();
		assertEquals(ResponseCode.SUCCESSFUL, inqResponse.getString(39));
		
		ISOMsg payRequest = (ISOMsg) inqResponse.clone();
		payRequest.setMTI(MTIConstants.PAYMENT_REQUEST);
		payRequest.unset(39);
		
		String bit48Inquiry = inqResponse.getString(48);
		String billstatus = bit48Inquiry.substring(19, 20);
		
		payRequest.set(48, bit48Inquiry.substring(0,20) + billstatus + bit48Inquiry.substring(20));
		channel.connect();
		channel.send(payRequest);
		ISOMsg payResponse = channel.receive();
		channel.disconnect();
		assertEquals(ResponseCode.SUCCESSFUL, payResponse.getString(39));
		
	}
}
