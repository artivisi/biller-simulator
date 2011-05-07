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

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.hibernate.SessionFactory;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import com.artivisi.ppob.simulator.dto.GeneratorTagihanPascabayar;
import com.artivisi.ppob.simulator.entity.Bank;
import com.artivisi.ppob.simulator.entity.Mitra;
import com.artivisi.ppob.simulator.entity.Pelanggan;
import com.artivisi.ppob.simulator.entity.PembayaranPascabayar;
import com.artivisi.ppob.simulator.entity.TagihanPascabayar;
import com.artivisi.ppob.simulator.service.PpobSimulatorService;


@Service("ppobSimulatorService")
@Transactional
public class PpobSimulatorServiceImpl implements PpobSimulatorService {

	@Autowired private SessionFactory sessionFactory;
	
	@Override
	public void save(Bank bank) {
		sessionFactory.getCurrentSession().saveOrUpdate(bank);
	}

	@Override
	public void delete(Bank bank) {
		if(bank == null || !StringUtils.hasText(bank.getId())) {
			return;
		}
		
		sessionFactory.getCurrentSession().delete(bank);
	}

	@Override
	public Bank findBankById(String id) {
		if(!StringUtils.hasText(id)) return null;
		return (Bank) sessionFactory.getCurrentSession().get(Bank.class, id);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Bank> findAllBank() {
		return sessionFactory.getCurrentSession().createQuery("from Bank order by kode").list();
	}

	@Override
	public Bank findBankByKode(String kode) {
		if(!StringUtils.hasText(kode)) return null;
		return (Bank) sessionFactory.getCurrentSession().createQuery("from Bank where kode = :kode")
		.setString("kode", kode.trim())
		.uniqueResult();
	}
	
	@Override
	public void save(Mitra mitra) {
		sessionFactory.getCurrentSession().saveOrUpdate(mitra);
	}

	@Override
	public void delete(Mitra mitra) {
		if(mitra == null || !StringUtils.hasText(mitra.getId())) {
			return;
		}
		
		sessionFactory.getCurrentSession().delete(mitra);
	}

	@Override
	public Mitra findMitraById(String id) {
		if(!StringUtils.hasText(id)) return null;
		return (Mitra) sessionFactory.getCurrentSession().get(Mitra.class, id);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Mitra> findAllMitra() {
		return sessionFactory.getCurrentSession().createQuery("from Mitra order by kode").list();
	}

	@Override
	public Mitra findMitraByKode(String kode) {
		if(!StringUtils.hasText(kode)) return null;
		return (Mitra) sessionFactory.getCurrentSession().createQuery("from Mitra where kode = :kode")
		.setString("kode", kode.trim())
		.uniqueResult();
	}
	
	
	@Override
	public void save(Pelanggan pelanggan) {
		sessionFactory.getCurrentSession().saveOrUpdate(pelanggan);
	}

	@Override
	public void delete(Pelanggan pelanggan) {
		if(pelanggan == null || !StringUtils.hasText(pelanggan.getId())) {
			return;
		}
		
		sessionFactory.getCurrentSession().createQuery("delete from PembayaranPascabayar p where p.tagihanPascabayar.id in (select t.id from TagihanPascabayar t where t.pelanggan.id = :pelanggan)")
		.setString("pelanggan", pelanggan.getId())
		.executeUpdate();
		
		sessionFactory.getCurrentSession().createQuery("delete from TagihanPascabayar t where t.pelanggan.id = :pelanggan")
		.setString("pelanggan", pelanggan.getId())
		.executeUpdate();
		
		sessionFactory.getCurrentSession().delete(pelanggan);
	}

	@Override
	public Pelanggan findPelangganById(String id) {
		if(!StringUtils.hasText(id)) return null;
		return (Pelanggan) sessionFactory.getCurrentSession().get(Pelanggan.class, id);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Pelanggan> findAllPelanggan() {
		return sessionFactory.getCurrentSession().createQuery("from Pelanggan order by idpel, nama").list();
	}

	@Override
	public Pelanggan findPelangganByIdpel(String idpel) {
		if(!StringUtils.hasText(idpel)) return null;
		return (Pelanggan) sessionFactory.getCurrentSession().createQuery("from Pelanggan where idpel = :idpel")
		.setString("idpel", idpel.trim())
		.uniqueResult();
	}

	@Override
	public Pelanggan findPelangganByMeterNumber(String meternum) {
		if(!StringUtils.hasText(meternum)) return null;
		return (Pelanggan) sessionFactory.getCurrentSession().createQuery("from Pelanggan where meterNumber = :meternum")
		.setString("meternum", meternum.trim())
		.uniqueResult();
	}

	@Override
	public void save(TagihanPascabayar tagihanPascabayar) {
		sessionFactory.getCurrentSession().saveOrUpdate(tagihanPascabayar);
	}

	@Override
	public void delete(TagihanPascabayar tagihanPascabayar) {
		if(tagihanPascabayar == null || !StringUtils.hasText(tagihanPascabayar.getId())) return;
		
		sessionFactory.getCurrentSession().createQuery("delete from PembayaranPascabayar p where p.tagihanPascabayar.id = :tagihan")
		.setString("tagihan", tagihanPascabayar.getId())
		.executeUpdate();
		
		sessionFactory.getCurrentSession().delete(tagihanPascabayar);
	}

	@Override
	public List<TagihanPascabayar> findTagihan(Pelanggan pelanggan) {
		if(pelanggan == null || !StringUtils.hasText(pelanggan.getId())) {
			return new ArrayList<TagihanPascabayar>();
		}
		
		return findTagihan(pelanggan, false);
	}
	
	@SuppressWarnings("unchecked")
	private List<TagihanPascabayar> findTagihan(Pelanggan pelanggan, Boolean lunas) {
		return sessionFactory.getCurrentSession()
		.createQuery("from TagihanPascabayar t where t.pelanggan.id = :pelanggan and t.lunas = :lunas order by t.billPeriod")
		.setString("pelanggan", pelanggan.getId())
		.setBoolean("lunas", lunas)
		.list();
	}

	@Override
	public void generatePascabayar(GeneratorTagihanPascabayar generator) {
		// hapus dulu data existing
		sessionFactory.getCurrentSession().createQuery("delete from PembayaranPascabayar").executeUpdate();
		sessionFactory.getCurrentSession().createQuery("delete from TagihanPascabayar").executeUpdate();
		sessionFactory.getCurrentSession().createQuery("delete from Pelanggan").executeUpdate();
		
		// insert pelanggan normal
		Integer current = 0;
		for (int i=0; i<generator.getNormal(); i++){
			generateTagihanNormal(current, i);
			current++;
		}
		
	}

	private void generateTagihanNormal(Integer current, int i) {
		Pelanggan p = new Pelanggan();
		p.setNama("Pelang'gan Dumm\"y "+current);
		
		TagihanPascabayar t = new TagihanPascabayar();
		t.setPelanggan(p);
		t.setBillPeriod(new Date());
		t.setDueDate(new DateTime().dayOfMonth().setCopy(20).toDate());
		t.setMeterReadDate(new DateTime().minusDays(10).toDate());
		t.setBill(new BigDecimal(Math.random() * 100000).setScale(2, RoundingMode.HALF_EVEN));
		t.setVat(t.getBill().divide(new BigDecimal(10)));
		
		Integer prev = Double.valueOf(Math.random() * 100).intValue();
		Integer curr = prev + Double.valueOf(Math.random() * 10).intValue();
		
		t.setPreviousMeterRead1(String.valueOf(prev));
		t.setPreviousMeterRead2(String.valueOf(prev));
		t.setPreviousMeterRead3(String.valueOf(prev));
		t.setCurrentMeterRead1(String.valueOf(curr));
		t.setCurrentMeterRead2(String.valueOf(curr));
		t.setCurrentMeterRead3(String.valueOf(curr));
		
		if(i%4==0){
			p.setIdpel("51"+org.apache.commons.lang.StringUtils.leftPad(current.toString(), 10, "0"));
			p.setMeterNumber("51"+org.apache.commons.lang.StringUtils.leftPad(current.toString(), 9, "0"));
			p.setServiceUnit("51");
			p.setServiceUnitPhone("51-1234567890");
			p.setPowerConsumingCategory("900");
			p.setSubscriberSegmentation("R1");
			t.setInsentif(t.getBill().multiply(new BigDecimal(0.8)));
		}else if(i%3==0){
			p.setIdpel("52"+org.apache.commons.lang.StringUtils.leftPad(current.toString(), 10, "0"));
			p.setMeterNumber("52"+org.apache.commons.lang.StringUtils.leftPad(current.toString(), 9, "0"));
			p.setServiceUnit("52");
			p.setServiceUnitPhone("52-1234567890");
			p.setPowerConsumingCategory("1300");
			p.setSubscriberSegmentation("R2");
			t.setInsentif(t.getBill().multiply(new BigDecimal(-0.8)));
		} else if(i%2==0){
			p.setIdpel("53"+org.apache.commons.lang.StringUtils.leftPad(current.toString(), 10, "0"));
			p.setMeterNumber("53"+org.apache.commons.lang.StringUtils.leftPad(current.toString(), 9, "0"));
			p.setServiceUnit("53");
			p.setServiceUnitPhone("53-1234567890");
			p.setPowerConsumingCategory("2000");
			p.setSubscriberSegmentation("S1");
		} else {
			p.setIdpel("54"+org.apache.commons.lang.StringUtils.leftPad(current.toString(), 10, "0"));
			p.setMeterNumber("54"+org.apache.commons.lang.StringUtils.leftPad(current.toString(), 9, "0"));
			p.setServiceUnit("54");
			p.setServiceUnitPhone("54-1234567890");
			p.setPowerConsumingCategory("2500");
			p.setSubscriberSegmentation("S2");
		}
		
		sessionFactory.getCurrentSession().saveOrUpdate(p);
		sessionFactory.getCurrentSession().saveOrUpdate(t);
	}

	@Override
	public void save(PembayaranPascabayar pembayaranPascabayar) {
		if(pembayaranPascabayar.getTagihanPascabayar().getLunas()) {
			throw new IllegalStateException("Sudah Lunas");
		}
		
		pembayaranPascabayar.getTagihanPascabayar().setLunas(true);
		sessionFactory.getCurrentSession().saveOrUpdate(pembayaranPascabayar.getTagihanPascabayar());
		
		sessionFactory.getCurrentSession().saveOrUpdate(pembayaranPascabayar);
	}

	@Override
	public void delete(PembayaranPascabayar pembayaranPascabayar) {
		if(pembayaranPascabayar == null || !StringUtils.hasText(pembayaranPascabayar.getId())) return;
		sessionFactory.getCurrentSession().delete(pembayaranPascabayar);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<PembayaranPascabayar> findPembayaranPascabayar(Date tanggal,String switcher) {
		if(tanggal == null || !StringUtils.hasText(switcher)) return new ArrayList<PembayaranPascabayar>();
		
		return sessionFactory.getCurrentSession()
		.createQuery("from PembayaranPascabayar p where p.tanggalTransaksi = :tanggal and p.switcher = :switcher order by p.waktuTransaksi")
		.setDate("tanggal", tanggal)
		.setString("switcher", switcher.trim())
		.list();
	}

}
