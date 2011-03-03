package com.artivisi.ppob.simulator.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

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

}
