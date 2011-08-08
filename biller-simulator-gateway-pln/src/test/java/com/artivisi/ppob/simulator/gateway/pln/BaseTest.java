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

import com.artivisi.biller.simulator.gateway.pln.jpos.PlnChannel;
import com.artivisi.biller.simulator.gateway.pln.jpos.PlnPackager;

public abstract class BaseTest {
	
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
				new FlatXmlDataSetBuilder().build(new File("src/test/resources/pelanggan.xml")));
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
