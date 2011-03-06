package com.artivisi.ppob.simulator.gateway.pln;

import static org.junit.Assert.assertEquals;

import org.jpos.iso.ISOMsg;
import org.junit.Test;

import com.artivisi.ppob.simulator.gateway.pln.constants.MTIConstants;
import com.artivisi.ppob.simulator.gateway.pln.jpos.PlnChannel;


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
