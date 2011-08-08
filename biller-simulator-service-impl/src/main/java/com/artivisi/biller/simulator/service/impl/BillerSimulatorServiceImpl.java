package com.artivisi.biller.simulator.service.impl;

import java.util.List;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import com.artivisi.biller.simulator.entity.Bank;
import com.artivisi.biller.simulator.entity.Mitra;
import com.artivisi.biller.simulator.service.BillerSimulatorService;

@Service("billerSimulatorService") @Transactional
public class BillerSimulatorServiceImpl implements BillerSimulatorService {
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
}
