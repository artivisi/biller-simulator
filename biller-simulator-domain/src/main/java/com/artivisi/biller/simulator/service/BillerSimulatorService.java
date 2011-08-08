package com.artivisi.biller.simulator.service;

import java.util.List;

import com.artivisi.biller.simulator.entity.Bank;
import com.artivisi.biller.simulator.entity.Mitra;

public interface BillerSimulatorService {
	public void save(Bank bank);
	public void delete(Bank bank);
	public Bank findBankById(String id);
	public Bank findBankByKode(String kode);
	public List<Bank> findAllBank();

	public void save(Mitra mitra);
	public void delete(Mitra mitra);
	public Mitra findMitraById(String id);
	public Mitra findMitraByKode(String kode);
	public List<Mitra> findAllMitra();
}
