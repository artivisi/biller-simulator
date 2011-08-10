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
