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
package com.artivisi.biller.simulator.service;

import java.util.Date;
import java.util.List;

import com.artivisi.biller.simulator.entity.Pelanggan;
import com.artivisi.biller.simulator.entity.PembelianListrikPrabayar;
import com.artivisi.biller.simulator.entity.TokenUnsold;

public interface PlnPrabayarSimulatorService {
	public void save(TokenUnsold tokenUnsold);
	public List<TokenUnsold> findTokenUnsoldByNoMeter(String noMeter);
	public List<TokenUnsold> findTokenUnsoldByIdPelanggan(String idPelanggan);
	
	public String generateNomerToken();
	public void save(PembelianListrikPrabayar transaksi);
	public List<PembelianListrikPrabayar> findPembelianListrikPrabayar(Pelanggan p, Integer start, Integer rows);
	public List<PembelianListrikPrabayar> findPembelianListrikPrabayar(Date mulai, Date sampai, Integer start, Integer rows);
	public List<PembelianListrikPrabayar> findPembelianListrikPrabayar(Date mulai, Date sampai, String switcher, Integer start, Integer rows);
	
	public Long countPembelianListrikPrabayar(Pelanggan p, Integer start, Integer rows);
	public Long countPembelianListrikPrabayar(Date mulai, Date sampai, Integer start, Integer rows);
	public Long countPembelianListrikPrabayar(Date mulai, Date sampai, String switcher, Integer start, Integer rows);
	
}
