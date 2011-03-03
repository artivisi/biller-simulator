package com.artivisi.ppob.simulator.service.impl;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.io.File;
import java.sql.Connection;
import java.util.List;

import javax.sql.DataSource;

import org.dbunit.database.DatabaseConnection;
import org.dbunit.dataset.xml.FlatXmlDataSetBuilder;
import org.dbunit.operation.DatabaseOperation;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.artivisi.ppob.simulator.entity.Pelanggan;
import com.artivisi.ppob.simulator.service.PpobSimulatorService;

public class PpobSimulatorServiceImplTest {

	private static PpobSimulatorService service;
	private static DataSource dataSource;
	
	@BeforeClass
	public static void init(){
		AbstractApplicationContext ctx = new ClassPathXmlApplicationContext("classpath*:com/artivisi/**/applicationContext.xml");
		ctx.registerShutdownHook();
		
		service = ctx.getBean(PpobSimulatorService.class);
		dataSource =  ctx.getBean(DataSource.class);
	}
	
	@Before
	public void resetDatabase() throws Exception {
		Connection conn = dataSource.getConnection();
		DatabaseOperation.CLEAN_INSERT.execute(new DatabaseConnection(conn), 
				new FlatXmlDataSetBuilder().build(new File("src/test/resources/pelanggan.xml")));
	}
	
	@Test
	public void testSave() {
		Pelanggan p = new Pelanggan();
		p.setIdpel("123456789010");
		p.setMeterNumber("12345678900");
		p.setNama("Pelanggan Dum'my");
		p.setPowerConsumingCategory("1200");
		p.setSubscriberSegmentation("R1");
		p.setServiceUnit("51");
		p.setServiceUnitPhone("1234567890");
		
		service.save(p);
		assertNotNull(p.getId());
	}

	@Test
	public void testDelete() {
		Pelanggan p = service.findPelangganById("abc");
		assertNotNull(p);
		service.delete(p);
		
		
		Pelanggan p2 = service.findPelangganById("abc");
		assertNull(p2);
	}

	@Test
	public void testFindAllPelanggan() {
		List<Pelanggan> all = service.findAllPelanggan();
		assertTrue(all.size() == 3);
	}

	@Test
	public void testFindPelangganByIdpel() {
		assertNull(service.findPelangganByIdpel(null));
		assertNull(service.findPelangganByIdpel(""));
		assertNull(service.findPelangganByIdpel("210987654321"));
		assertNotNull(service.findPelangganByIdpel("123456789012"));
	}

	@Test
	public void testFindPelangganByMeterNumber() {
		assertNull(service.findPelangganByMeterNumber(null));
		assertNull(service.findPelangganByMeterNumber(""));
		assertNull(service.findPelangganByMeterNumber("10987654321"));
		assertNotNull(service.findPelangganByMeterNumber("12345678901"));
	}

}
