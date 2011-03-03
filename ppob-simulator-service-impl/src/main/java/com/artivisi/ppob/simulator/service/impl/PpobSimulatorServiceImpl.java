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
import com.artivisi.ppob.simulator.entity.Pelanggan;
import com.artivisi.ppob.simulator.entity.TagihanPascabayar;
import com.artivisi.ppob.simulator.service.PpobSimulatorService;


@Service("ppobSimulatorService")
@Transactional
public class PpobSimulatorServiceImpl implements PpobSimulatorService {

	@Autowired private SessionFactory sessionFactory;
	
	@Override
	public void save(Pelanggan pelanggan) {
		sessionFactory.getCurrentSession().saveOrUpdate(pelanggan);
	}

	@Override
	public void delete(Pelanggan pelanggan) {
		if(pelanggan == null || !StringUtils.hasText(pelanggan.getId())) {
			return;
		}
		
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
		sessionFactory.getCurrentSession().delete(tagihanPascabayar);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<TagihanPascabayar> findTagihan(Pelanggan pelanggan) {
		if(pelanggan == null || !StringUtils.hasText(pelanggan.getId())) {
			return new ArrayList<TagihanPascabayar>();
		}
		
		return sessionFactory.getCurrentSession()
		.createQuery("from TagihanPascabayar t where t.pelanggan.id = :pelanggan order by t.billPeriod")
		.setString("pelanggan", pelanggan.getId())
		.list();
	}

	@Override
	public void generatePascabayar(GeneratorTagihanPascabayar generator) {
		// hapus dulu data existing
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

}
