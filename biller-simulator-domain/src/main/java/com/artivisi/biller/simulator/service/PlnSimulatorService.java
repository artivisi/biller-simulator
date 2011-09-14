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

import com.artivisi.biller.simulator.dto.GeneratorTagihanPascabayar;
import com.artivisi.biller.simulator.entity.Pelanggan;
import com.artivisi.biller.simulator.entity.PembayaranPascabayar;
import com.artivisi.biller.simulator.entity.TagihanNontaglis;
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
	
	public void save(TagihanNontaglis tagihanNontaglis);
	public void delete(TagihanNontaglis tagihanNontaglis);
	public List<TagihanNontaglis> findTagihanNontaglis(TagihanNontaglis tagihanNontaglis);
	
	public void generatePascabayar(GeneratorTagihanPascabayar generator);
	
	public void save(PembayaranPascabayar pembayaranPascabayar);
	public void delete(PembayaranPascabayar pembayaranPascabayar);
	public List<PembayaranPascabayar> findPembayaranPascabayar(Date tanggal, String switcher);
}
