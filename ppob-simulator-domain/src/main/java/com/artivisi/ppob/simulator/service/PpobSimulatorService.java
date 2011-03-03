package com.artivisi.ppob.simulator.service;

import java.util.List;

import com.artivisi.ppob.simulator.entity.Pelanggan;

public interface PpobSimulatorService {
	public void save(Pelanggan pelanggan);
	public void delete(Pelanggan pelanggan);
	public Pelanggan findPelangganById(String id);
	public Pelanggan findPelangganByIdpel(String idpel);
	public Pelanggan findPelangganByMeterNumber(String meternum);
	public List<Pelanggan> findAllPelanggan();
}
