package com.artivisi.ppob.simulator.gateway.pln;

import org.jpos.util.Log4JListener;

import com.artivisi.ppob.simulator.gateway.pln.jpos.PlnChannel;
import com.artivisi.ppob.simulator.gateway.pln.jpos.PlnPackager;

public class BaseTest {
	
	private String host = "localhost";
	private Integer port = 11111;
	private Integer timeout = 30000;
	
	public PlnChannel createChannel() throws Exception {
		PlnChannel channel = new PlnChannel(host, port, new PlnPackager());
		channel.setTimeout(timeout);
		
		org.jpos.util.Logger jposLogger = new org.jpos.util.Logger();
	    Log4JListener log4JListener = new Log4JListener();
	    log4JListener.setLevel("info");
	    jposLogger.addListener(log4JListener);
	    channel.setLogger(jposLogger, "pln-channel");
		
		return channel;
	}
}
