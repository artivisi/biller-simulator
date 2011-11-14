package com.artivisi.biller.simulator.service.impl;

import java.util.Date;
import java.util.List;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.artivisi.biller.simulator.entity.Pelanggan;
import com.artivisi.biller.simulator.entity.PembelianListrikPrabayar;
import com.artivisi.biller.simulator.entity.TokenUnsold;
import com.artivisi.biller.simulator.service.PlnPrabayarSimulatorService;

@Service("plnPrabayarSimulatorService") 
@Transactional
public class PlnPrabayarSimulatorServiceImpl implements PlnPrabayarSimulatorService{
	
	@Autowired
	private SessionFactory sessionFactory ;

	@Override
	public void save(TokenUnsold tokenUnsold) {
		sessionFactory.getCurrentSession().save(tokenUnsold);

	}

	@Override
	public List<TokenUnsold> findTokenUnsoldByNoMeter(String noMeter) {
		return null ;
	}

	@Override
	public List<TokenUnsold> findTokenUnsoldByIdPelanggan(String idPelanggan) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String generateNomerToken() {
		// remove the list of user
//		sessionFactory.getCurrentSession().createQuery("delete from PembayaranPascabayar").executeUpdate();
//		sessionFactory.getCurrentSession().createQuery("delete from TagihanPascabayar").executeUpdate();
//		sessionFactory.getCurrentSession().createQuery("delete from Pelanggan").executeUpdate();
//		
		sessionFactory.getCurrentSession().createQuery("delete from ").executeUpdate();
		return null ;
		
	}

	@Override
	public void save(PembelianListrikPrabayar transaksi) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public List<PembelianListrikPrabayar> findPembelianListrikPrabayar(
			Pelanggan p, Integer start, Integer rows) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<PembelianListrikPrabayar> findPembelianListrikPrabayar(
			Date mulai, Date sampai, Integer start, Integer rows) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<PembelianListrikPrabayar> findPembelianListrikPrabayar(
			Date mulai, Date sampai, String switcher, Integer start,
			Integer rows) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Long countPembelianListrikPrabayar(Pelanggan p, Integer start,
			Integer rows) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Long countPembelianListrikPrabayar(Date mulai, Date sampai,
			Integer start, Integer rows) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Long countPembelianListrikPrabayar(Date mulai, Date sampai,
			String switcher, Integer start, Integer rows) {
		// TODO Auto-generated method stub
		return null;
	}

}
