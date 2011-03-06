package com.artivisi.ppob.simulator.service;

import java.util.Date;
import java.util.List;

import com.artivisi.ppob.simulator.dto.GeneratorTagihanPascabayar;
import com.artivisi.ppob.simulator.entity.Bank;
import com.artivisi.ppob.simulator.entity.Mitra;
import com.artivisi.ppob.simulator.entity.Pelanggan;
import com.artivisi.ppob.simulator.entity.PembayaranPascabayar;
import com.artivisi.ppob.simulator.entity.TagihanPascabayar;

public interface PpobSimulatorService {
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
	
	public void save(Pelanggan pelanggan);
	public void delete(Pelanggan pelanggan);
	public Pelanggan findPelangganById(String id);
	public Pelanggan findPelangganByIdpel(String idpel);
	public Pelanggan findPelangganByMeterNumber(String meternum);
	public List<Pelanggan> findAllPelanggan();
	
	public void save(TagihanPascabayar tagihanPascabayar);
	public void delete(TagihanPascabayar tagihanPascabayar);
	public List<TagihanPascabayar> findTagihan(Pelanggan pelanggan);
	
	public void generatePascabayar(GeneratorTagihanPascabayar generator);
	
	public void save(PembayaranPascabayar pembayaranPascabayar);
	public void delete(PembayaranPascabayar pembayaranPascabayar);
	public List<PembayaranPascabayar> findPembayaranPascabayar(Date tanggal, String switcher);
}
