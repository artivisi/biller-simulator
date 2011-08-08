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
package com.artivisi.ppob.simulator.gateway.pln;

import static org.junit.Assert.assertEquals;

import org.jpos.iso.ISOMsg;
import org.junit.Test;

import com.artivisi.biller.simulator.gateway.pln.constants.MTIConstants;
import com.artivisi.biller.simulator.gateway.pln.jpos.PlnChannel;


public class NetworkManagementTest extends BaseTest {
	
	
	@Test
	public void testSignon() throws Exception {
		ISOMsg msg = new ISOMsg();
		msg.setMTI(MTIConstants.NETWORK_MANAGEMENT_REQUEST);
		msg.set(70, "001");
		
		PlnChannel channel = createChannel();
		channel.connect();
		channel.send(msg);
		
		ISOMsg response = channel.receive();
		assertEquals("0000", response.getString(39));
		channel.disconnect();
	}
}
