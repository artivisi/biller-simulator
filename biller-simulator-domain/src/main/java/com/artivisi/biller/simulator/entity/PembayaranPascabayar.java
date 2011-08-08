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
package com.artivisi.biller.simulator.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.GenericGenerator;

@Entity @Table(name="pembayaran_pascabayar")
public class PembayaranPascabayar {
	@Id @GeneratedValue(generator = "system-uuid")
    @GenericGenerator(name = "system-uuid", strategy = "uuid")
	private String id;
	
	@OneToOne @JoinColumn(name="id_tagihan_pascabayar", nullable=false)
	private TagihanPascabayar tagihanPascabayar;
	
	@Column(nullable=false, name="waktu_transaksi")
	@Temporal(TemporalType.TIMESTAMP)
	private Date waktuTransaksi = new Date();
	@Temporal(TemporalType.DATE)
	@Column(nullable=false, name="tanggal_transaksi")
	private Date tanggalTransaksi = new Date();
	@Column(nullable=false)
	private String bank;
	@Column(nullable=false)
	private String switcher;
	@Column(nullable=false)
	private String loket;
	@Column(nullable=false)
	private String operator;
	@Column(nullable=false, name="merchant_category")
	private String merchantCategory;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public TagihanPascabayar getTagihanPascabayar() {
		return tagihanPascabayar;
	}
	public void setTagihanPascabayar(TagihanPascabayar tagihanPascabayar) {
		this.tagihanPascabayar = tagihanPascabayar;
	}
	public Date getWaktuTransaksi() {
		return waktuTransaksi;
	}
	public void setWaktuTransaksi(Date waktuTransaksi) {
		this.waktuTransaksi = waktuTransaksi;
	}
	public Date getTanggalTransaksi() {
		return tanggalTransaksi;
	}
	public void setTanggalTransaksi(Date tanggalTransaksi) {
		this.tanggalTransaksi = tanggalTransaksi;
	}
	public String getBank() {
		return bank;
	}
	public void setBank(String bank) {
		this.bank = bank;
	}
	public String getSwitcher() {
		return switcher;
	}
	public void setSwitcher(String switcher) {
		this.switcher = switcher;
	}
	public String getLoket() {
		return loket;
	}
	public void setLoket(String loket) {
		this.loket = loket;
	}
	public String getOperator() {
		return operator;
	}
	public void setOperator(String operator) {
		this.operator = operator;
	}
	public String getMerchantCategory() {
		return merchantCategory;
	}
	public void setMerchantCategory(String merchantCategory) {
		this.merchantCategory = merchantCategory;
	}
}
