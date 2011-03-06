package com.artivisi.ppob.simulator.gateway.pln;

import java.io.File;
import java.sql.Connection;

import javax.sql.DataSource;

import org.dbunit.database.DatabaseConnection;
import org.dbunit.dataset.xml.FlatXmlDataSetBuilder;
import org.dbunit.operation.DatabaseOperation;
import org.jpos.util.Log4JListener;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.artivisi.ppob.simulator.gateway.pln.jpos.PlnChannel;
import com.artivisi.ppob.simulator.gateway.pln.jpos.PlnPackager;

public class BaseTest {
	
	private static AbstractApplicationContext ctx;
	private static DataSource dataSource;
	
	private String host = "localhost";
	private Integer port = 11111;
	private Integer timeout = 30000;
	
	@BeforeClass
	public static void startPlnGateway(){
		ctx = new ClassPathXmlApplicationContext("classpath*:com/artivisi/**/applicationContext.xml");
		ctx.registerShutdownHook();
		
		dataSource = ctx.getBean(DataSource.class);
	}
	
	@AfterClass
	public static void stopPlnGateway(){
		ctx.destroy();
	}
	
	@Before
	public void resetDatabase() throws Exception {
		Connection conn = dataSource.getConnection();
		DatabaseOperation.CLEAN_INSERT.execute(new DatabaseConnection(conn), 
				new FlatXmlDataSetBuilder().build(new File("../ppob-simulator-service-impl/src/test/resources/pelanggan.xml")));
	}
	
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
