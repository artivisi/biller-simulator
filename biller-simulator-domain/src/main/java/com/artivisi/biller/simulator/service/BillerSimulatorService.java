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
