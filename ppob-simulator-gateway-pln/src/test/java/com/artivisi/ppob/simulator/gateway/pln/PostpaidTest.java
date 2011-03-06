package com.artivisi.ppob.simulator.gateway.pln;

import java.util.Date;
import static org.junit.Assert.*;

import org.joda.time.format.DateTimeFormat;
import org.jpos.iso.ISOMsg;
import org.junit.Test;

import com.artivisi.ppob.simulator.gateway.pln.constants.MTIConstants;
import com.artivisi.ppob.simulator.gateway.pln.jpos.PlnChannel;

public class PostpaidTest extends BaseTest {
	
	@Test
	public void testInquiry() throws Exception {
		String idpel = "123456789011";
		String switcher = "ARTIVIS";
		
		ISOMsg request = new ISOMsg();
		request.setMTI(MTIConstants.INQUIRY_REQUEST);
		request.set(2, "51501");
		request.set(11, "123456789012");
		request.set(12, DateTimeFormat.forPattern("yyyyMMddHHmmss").print(new Date().getTime()));
		request.set(26, "6012");
		request.set(32, "BANKABC");
		request.set(48, switcher + idpel);
		
		PlnChannel channel = createChannel();
		channel.connect();
		channel.send(request);
		ISOMsg response = channel.receive();
		channel.disconnect();
		assertEquals("0000", response.getString(39));
		
	}
}
