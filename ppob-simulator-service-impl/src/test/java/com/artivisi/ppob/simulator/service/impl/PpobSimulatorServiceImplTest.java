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
package com.artivisi.ppob.simulator.service.impl;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.io.File;
import java.math.BigDecimal;
import java.sql.Connection;
import java.util.Date;
import java.util.List;

import javax.sql.DataSource;

import org.dbunit.database.DatabaseConnection;
import org.dbunit.dataset.xml.FlatXmlDataSetBuilder;
import org.dbunit.operation.DatabaseOperation;
import org.joda.time.DateTime;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.artivisi.ppob.simulator.dto.GeneratorTagihanPascabayar;
import com.artivisi.ppob.simulator.entity.Bank;
import com.artivisi.ppob.simulator.entity.Mitra;
import com.artivisi.ppob.simulator.entity.Pelanggan;
import com.artivisi.ppob.simulator.entity.PembayaranPascabayar;
import com.artivisi.ppob.simulator.entity.TagihanPascabayar;
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
	public void testSaveBank(){
		Bank b = new Bank();
		b.setKode("BANKIRB");
		b.setNama("Bank IRB");
		
		service.save(b);
		assertNotNull(b.getId());
	}
	
	@Test
	public void testDeleteBank(){
		Bank b = service.findBankById("abc");
		assertNotNull(b);
		service.delete(b);
		assertNull(service.findBankById("abc"));
	}
	
	@Test
	public void testFindAllBank(){
		assertTrue(service.findAllBank().size() == 1);
	}

	@Test
	public void testBankByKode(){
		assertNotNull(service.findBankByKode("BANKABC"));
		assertNull(service.findBankByKode("BANKBCA"));
	}
	
	@Test
	public void testSaveMitra(){
		Mitra b = new Mitra();
		b.setKode("JMI1234");
		b.setNama("JMI");
		
		service.save(b);
		assertNotNull(b.getId());
	}
	
	@Test
	public void testDeleteMitra(){
		Mitra b = service.findMitraById("abc");
		assertNotNull(b);
		service.delete(b);
		assertNull(service.findMitraById("abc"));
	}
	
	@Test
	public void testFindAllMitra(){
		assertTrue(service.findAllMitra().size() == 1);
	}

	@Test
	public void testMitraByKode(){
		assertNotNull(service.findMitraByKode("ARTIVIS"));
		assertNull(service.findMitraByKode("JMI1234"));
	}
	
	
	@Test
	public void testSavePelanggan() {
		Pelanggan p = createPelanggan();
		service.save(p);
		assertNotNull(p.getId());
	}

	@Test
	public void testDeletePelanggan() {
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
	
	@Test
	public void testSaveTagihanPascabayar(){
		Pelanggan p = service.findPelangganById("def");
		TagihanPascabayar t = createTagihanPascabayar(p);
		service.save(t);
		assertNotNull(t.getId());
	}
	
	@Test
	public void testDeleteTagihanPascabayar() {
		Pelanggan p = service.findPelangganById("abc");
		List<TagihanPascabayar> hasil = service.findTagihan(p);
		
		TagihanPascabayar t = hasil.get(0);
		service.delete(t);
		
		assertTrue(service.findTagihan(p).size() == 1);
	}
	
	@Test
	public void testFindTagihanByPelanggan(){
		Pelanggan p = service.findPelangganById("abc");
		List<TagihanPascabayar> hasil = service.findTagihan(p);
		assertTrue(hasil.size() == 2);
	}
	
	@Test
	public void testGenerate(){
		service.generatePascabayar(new GeneratorTagihanPascabayar());
	}
	
	@Test
	public void testSavePembayaran(){
		TagihanPascabayar t = service.findTagihan(service.findPelangganById("abc")).get(1);
		PembayaranPascabayar p = createPembayaranPascabayar(t);
		service.save(p);
		assertNotNull(p.getId());
	}

	
	@Test
	public void testDeletePembayaran(){
		List<PembayaranPascabayar> hasil =service.findPembayaranPascabayar(new DateTime(2011,01,01,0,0,0,0).toDate(), "ARTIVISI");
		assertNotNull(hasil);
		assertTrue(hasil.size() == 1);
		PembayaranPascabayar p = hasil.get(0);
		service.delete(p);
		assertTrue(service.findPembayaranPascabayar(new DateTime(2011,01,01,0,0,0,0).toDate(), "ARTIVISI").size() == 0);
	}
	
	private PembayaranPascabayar createPembayaranPascabayar(TagihanPascabayar t){
		PembayaranPascabayar p = new PembayaranPascabayar();
		
		p.setTagihanPascabayar(t);
		p.setBank("BANKABC");
		p.setLoket("L-002");
		p.setMerchantCategory("6012");
		p.setOperator("endy");
		p.setSwitcher("ARTIVISI");
		
		return p;
	}
	
	private TagihanPascabayar createTagihanPascabayar(Pelanggan p) {
		TagihanPascabayar t = new TagihanPascabayar();
		t.setPelanggan(p);
		t.setBillPeriod(new Date());
		t.setDueDate(new Date());
		t.setMeterReadDate(new Date());
		
		t.setBill(new BigDecimal(99000));
		t.setVat(new BigDecimal(9900));
		t.setDenda(new BigDecimal(990));
		t.setInsentif(new BigDecimal(90000));

		t.setPreviousMeterRead1("123");
		t.setPreviousMeterRead2("123");
		t.setPreviousMeterRead3("123");
		t.setCurrentMeterRead1("456");
		t.setCurrentMeterRead2("456");
		t.setCurrentMeterRead3("456");
		return t;
	}

	private Pelanggan createPelanggan() {
		Pelanggan p = new Pelanggan();
		p.setIdpel("123456789010");
		p.setMeterNumber("12345678900");
		p.setNama("Pelanggan Dum'my");
		p.setPowerConsumingCategory("1200");
		p.setSubscriberSegmentation("R1");
		p.setServiceUnit("51");
		p.setServiceUnitPhone("1234567890");
		return p;
	}

}
