package com.artivisi.biller.simulator.service;

import java.util.Date;
import java.util.List;

import com.artivisi.biller.simulator.dto.GeneratorTagihanPascabayar;
import com.artivisi.biller.simulator.entity.Pelanggan;
import com.artivisi.biller.simulator.entity.PembayaranPascabayar;
import com.artivisi.biller.simulator.entity.TagihanPascabayar;

public interface PlnSimulatorService {
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
