/**
 * Copyright (C) ${year} ArtiVisi Intermedia <info@artivisi.com>
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
package com.artivisi.ppob.simulator.gateway.pln;

import static org.junit.Assert.assertEquals;

import java.util.Date;

import org.joda.time.format.DateTimeFormat;
import org.jpos.iso.ISOMsg;
import org.junit.Test;

import com.artivisi.biller.simulator.gateway.pln.constants.MTIConstants;
import com.artivisi.biller.simulator.gateway.pln.constants.ResponseCode;
import com.artivisi.biller.simulator.gateway.pln.jpos.PlnChannel;

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
