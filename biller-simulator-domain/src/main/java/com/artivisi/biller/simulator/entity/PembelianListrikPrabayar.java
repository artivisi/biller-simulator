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

import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.GenericGenerator;

@Entity @Table(name="pembelian_listrik_prabayar")
public class PembelianListrikPrabayar {
	@Id @GeneratedValue(generator = "system-uuid")
    @GenericGenerator(name = "system-uuid", strategy = "uuid")
	private String id;

	@ManyToOne @JoinColumn(name="id_pelanggan", nullable=false)
	private Pelanggan pelanggan;
	
	@Column(nullable=false, name="nilai_token")
	private BigDecimal nilaiToken;
	@Column(nullable=false, name="nomer_token")
	private String nomerToken;
	@Column(nullable=false, name="kwh")
	private BigDecimal kwh;
	
	
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
	public BigDecimal getNilaiToken() {
		return nilaiToken;
	}
	public void setNilaiToken(BigDecimal nilaiToken) {
		this.nilaiToken = nilaiToken;
	}
	public String getNomerToken() {
		return nomerToken;
	}
	public void setNomerToken(String nomerToken) {
		this.nomerToken = nomerToken;
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
	public Pelanggan getPelanggan() {
		return pelanggan;
	}
	public void setPelanggan(Pelanggan pelanggan) {
		this.pelanggan = pelanggan;
	}
	public BigDecimal getKwh() {
		return kwh;
	}
	public void setKwh(BigDecimal kwh) {
		this.kwh = kwh;
	}
	
	
}
