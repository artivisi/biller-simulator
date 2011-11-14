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
package com.artivisi.biller.simulator.service.impl;

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

import com.artivisi.biller.simulator.dto.GeneratorTagihanPascabayar;
import com.artivisi.biller.simulator.entity.Bank;
import com.artivisi.biller.simulator.entity.Mitra;
import com.artivisi.biller.simulator.entity.Pelanggan;
import com.artivisi.biller.simulator.entity.PembayaranPascabayar;
import com.artivisi.biller.simulator.entity.TagihanNontaglis;
import com.artivisi.biller.simulator.entity.TagihanNontaglisDetail;
import com.artivisi.biller.simulator.entity.TagihanPascabayar;
import com.artivisi.biller.simulator.service.BillerSimulatorService;
import com.artivisi.biller.simulator.service.PlnSimulatorService;

public class PpobSimulatorServiceImplTest {

	private static BillerSimulatorService billerService;
	private static PlnSimulatorService plnService;
	private static DataSource dataSource;
	
	@BeforeClass
	public static void init(){
		AbstractApplicationContext ctx = new ClassPathXmlApplicationContext("classpath*:com/artivisi/**/applicationContext.xml");
		ctx.registerShutdownHook();
		
		billerService = ctx.getBean(BillerSimulatorService.class);
		plnService = ctx.getBean(PlnSimulatorService.class);
		dataSource =  ctx.getBean(DataSource.class);
	}
	
	@Before
	public void resetDatabase() throws Exception {
		Connection conn = dataSource.getConnection();
		
		String sqlDropTableInquiryDetail = "drop table if exists inquiry_postpaid_response_detail";
		conn.createStatement().executeUpdate(sqlDropTableInquiryDetail);
		
		String sqlDropTableInquiry = "drop table if exists inquiry_postpaid_response";
		conn.createStatement().executeUpdate(sqlDropTableInquiry);
		
		
		DatabaseOperation.CLEAN_INSERT.execute(new DatabaseConnection(conn), 
				new FlatXmlDataSetBuilder().build(new File("src/test/resources/pelanggan.xml")));
	}
	
	@Test
	public void testSaveBank(){
		Bank b = new Bank();
		b.setKode("BANKIRB");
		b.setNama("Bank IRB");
		
		billerService.save(b);
		assertNotNull(b.getId());
	}
	
	@Test
	public void testDeleteBank(){
		Bank b = billerService.findBankById("abc");
		assertNotNull(b);
		billerService.delete(b);
		assertNull(billerService.findBankById("abc"));
	}
	
	@Test
	public void testFindAllBank(){
		assertTrue(billerService.findAllBank().size() == 1);
	}

	@Test
	public void testBankByKode(){
		assertNotNull(billerService.findBankByKode("BANKABC"));
		assertNull(billerService.findBankByKode("BANKBCA"));
	}
	
	@Test
	public void testSaveMitra(){
		Mitra b = new Mitra();
		b.setKode("JMI1234");
		b.setNama("JMI");
		
		billerService.save(b);
		assertNotNull(b.getId());
	}
	
	@Test
	public void testDeleteMitra(){
		Mitra b = billerService.findMitraById("abc");
		assertNotNull(b);
		billerService.delete(b);
		assertNull(billerService.findMitraById("abc"));
	}
	
	@Test
	public void testFindAllMitra(){
		assertTrue(billerService.findAllMitra().size() == 1);
	}

	@Test
	public void testMitraByKode(){
		assertNotNull(billerService.findMitraByKode("ARTIVIS"));
		assertNull(billerService.findMitraByKode("JMI1234"));
	}
	
	
	@Test
	public void testSavePelanggan() {
		Pelanggan p = createPelanggan();
		plnService.save(p);
		assertNotNull(p.getId());
	}

	@Test
	public void testDeletePelanggan() {
		Pelanggan p = plnService.findPelangganById("abc");
		assertNotNull(p);
		plnService.delete(p);
		
		Pelanggan p2 = plnService.findPelangganById("abc");
		assertNull(p2);
	}

	@Test
	public void testFindAllPelanggan() {
		List<Pelanggan> all = plnService.findAllPelanggan();
		assertTrue(all.size() == 3);
	}

	@Test
	public void testFindPelangganByIdpel() {
		assertNull(plnService.findPelangganByIdpel(null));
		assertNull(plnService.findPelangganByIdpel(""));
		assertNull(plnService.findPelangganByIdpel("210987654321"));
		assertNotNull(plnService.findPelangganByIdpel("123456789012"));
	}

	@Test
	public void testFindPelangganByMeterNumber() {
		assertNull(plnService.findPelangganByMeterNumber(null));
		assertNull(plnService.findPelangganByMeterNumber(""));
		assertNull(plnService.findPelangganByMeterNumber("10987654321"));
		assertNotNull(plnService.findPelangganByMeterNumber("12345678901"));
	}
	
	@Test
	public void testSaveTagihanPascabayar(){
		Pelanggan p = plnService.findPelangganById("def");
		TagihanPascabayar t = createTagihanPascabayar(p);
		plnService.save(t);
		assertNotNull(t.getId());
	}
	
	@Test
	public void testDeleteTagihanPascabayar() {
		Pelanggan p = plnService.findPelangganById("abc");
		List<TagihanPascabayar> hasil = plnService.findTagihan(p);
		
		TagihanPascabayar t = hasil.get(0);
		plnService.delete(t);
		
		assertTrue(plnService.findTagihan(p).size() == 1);
	}
	
	@Test
	public void testFindTagihanByPelanggan(){
		Pelanggan p = plnService.findPelangganById("abc");
		List<TagihanPascabayar> hasil = plnService.findTagihan(p);
		assertTrue(hasil.size() == 2);
	}
	
	@Test
	public void testGenerate(){
		plnService.generatePascabayar(new GeneratorTagihanPascabayar());
	}
	
	@Test
	public void testSavePembayaran(){
		TagihanPascabayar t = plnService.findTagihan(plnService.findPelangganById("abc")).get(1);
		PembayaranPascabayar p = createPembayaranPascabayar(t);
		plnService.save(p);
		assertNotNull(p.getId());
	}

	
	@Test
	public void testDeletePembayaran(){
		List<PembayaranPascabayar> hasil =plnService.findPembayaranPascabayar(new DateTime(2011,01,01,0,0,0,0).toDate(), "ARTIVISI");
		assertNotNull(hasil);
		assertTrue(hasil.size() == 1);
		PembayaranPascabayar p = hasil.get(0);
		plnService.delete(p);
		assertTrue(plnService.findPembayaranPascabayar(new DateTime(2011,01,01,0,0,0,0).toDate(), "ARTIVISI").size() == 0);
	}
	
	@Test
	public void testSaveTagihanNontaglis(){
		Pelanggan p = plnService.findPelangganById("def");
		TagihanNontaglis t = createTagihanNontaglis(p);
		plnService.save(t);
		assertNotNull(t.getId());
	}
	
	@Test
	public void testDeleteTagihanNontaglis(){
		TagihanNontaglis t = new TagihanNontaglis();
		assertNotNull(t);
		plnService.delete(t);
		assertNull(plnService.findTagihanNontaglis("956788"));
	}
	
	@Test
	public void testSaveTagihannontaglisDetail(){
		TagihanNontaglisDetail t = new TagihanNontaglisDetail();
		t.setTotalRepeat(new Integer(2));
		t.setCustomDetailUnit(new Integer(0));
		t.setCustomDetailCode(new Integer(0));
		t.setCustomDetailValueAmount(new Integer(0));
		plnService.save(t);
		assertNotNull(t.getId());
	}
	
	@Test
	public void testFindTagihanNontaglis(){
		assertNull(plnService.findTagihanNontaglis(null));
		assertNull(plnService.findTagihanNontaglis(""));
		assertNull(plnService.findTagihanNontaglis("956788"));
	}
	
//	@Test
//	public void testFindAllTagihanNontaglisDetail(){
//		assertTrue(plnService.findAllTagihanNontaglisDetail().size()==1);
//	}
	
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
	
	private TagihanNontaglis createTagihanNontaglis(Pelanggan p){
		TagihanNontaglis t = new TagihanNontaglis();
		t.setSwitcherId("xyz");
		t.setSubscriberName("Fanani M. Ihsan");
		t.setRegistrationNumber("987654567") ;
		t.setAreaCode(new Integer(876545)) ;
		t.setTransactionCode(new Integer(9567876)) ;
		t.setTransactionName("transaction");
		t.setRegistrationDate(new Date());
		t.setExpirationDate(new Date());
		t.setSubscriberId(new BigDecimal(74565656)) ;
		t.setPlnRefNumber("k09876567jjjfjh");
		t.setCaReceiptRefNumber("abcdefghij");
		t.setServiceUnit("fkakkkfas");
		t.setServiceUnitAdress("xyz");
		t.setServiceUnitPhone("021");
		t.setTotalTransactionAmountMinorUnit(new BigDecimal(0));
		t.setTotalTransactionAmount(new BigDecimal("90000"));
		t.setPlnBillMinorUnit(new BigDecimal("100000"));
		t.setRptag(new BigDecimal(80000));
		t.setAdminChargeUnit(new BigDecimal(1));
		t.setAdminCharge(new BigDecimal(1600));
		t.setMutationNumber(new Integer(0));
		t.setSubscriberSegmentation(new Integer(0));
		t.setPowerConsumingCategory(new Integer(950));
		return t;
	}

}
