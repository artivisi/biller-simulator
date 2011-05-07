/**
 * Copyright (C) ${year} ArtiVisi Intermedia <info@artivisi.com>
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
package com.artivisi.ppob.simulator.entity;

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

@Entity @Table(name="tagihan_pascabayar")
public class TagihanPascabayar {
	
	@Id @GeneratedValue(generator = "system-uuid")
    @GenericGenerator(name = "system-uuid", strategy = "uuid")
	private String id;
	
	@ManyToOne 
	@JoinColumn(name="id_pelanggan", nullable=false)
	private Pelanggan pelanggan;
	
	@Temporal(TemporalType.DATE)
	@Column(name="bill_period", nullable=false)
	private Date billPeriod = new Date();
	@Temporal(TemporalType.DATE)
	@Column(name="due_date", nullable=false)
	private Date dueDate = new Date();
	@Temporal(TemporalType.DATE)
	@Column(name="meter_read_date", nullable=false)
	private Date meterReadDate = new Date();
	
	@Column(nullable=false)
	private BigDecimal bill = BigDecimal.ZERO;
	@Column(nullable=false)
	private BigDecimal denda = BigDecimal.ZERO;
	@Column(nullable=false)
	private BigDecimal insentif = BigDecimal.ZERO;
	@Column(nullable=false)
	private BigDecimal vat = BigDecimal.ZERO;
	
	@Column(nullable=false, name="prev_meter_read_1")
	private String previousMeterRead1;
	@Column(nullable=false, name="prev_meter_read_2")
	private String previousMeterRead2;
	@Column(nullable=false, name="prev_meter_read_3")
	private String previousMeterRead3;
	@Column(nullable=false, name="curr_meter_read_1")
	private String currentMeterRead1;
	@Column(nullable=false, name="curr_meter_read_2")
	private String currentMeterRead2;
	@Column(nullable=false, name="curr_meter_read_3")
	private String currentMeterRead3;
	
	@Column(nullable=false)
	private Boolean lunas = Boolean.FALSE;
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public Boolean getLunas() {
		return lunas;
	}
	public void setLunas(Boolean lunas) {
		this.lunas = lunas;
	}
	public Pelanggan getPelanggan() {
		return pelanggan;
	}
	public void setPelanggan(Pelanggan pelanggan) {
		this.pelanggan = pelanggan;
	}
	public Date getBillPeriod() {
		return billPeriod;
	}
	public void setBillPeriod(Date billPeriod) {
		this.billPeriod = billPeriod;
	}
	public Date getDueDate() {
		return dueDate;
	}
	public void setDueDate(Date dueDate) {
		this.dueDate = dueDate;
	}
	public Date getMeterReadDate() {
		return meterReadDate;
	}
	public void setMeterReadDate(Date meterReadDate) {
		this.meterReadDate = meterReadDate;
	}
	public BigDecimal getBill() {
		return bill;
	}
	public void setBill(BigDecimal bill) {
		this.bill = bill;
	}
	public BigDecimal getDenda() {
		return denda;
	}
	public void setDenda(BigDecimal denda) {
		this.denda = denda;
	}
	public BigDecimal getInsentif() {
		return insentif;
	}
	public void setInsentif(BigDecimal insentif) {
		this.insentif = insentif;
	}
	public BigDecimal getVat() {
		return vat;
	}
	public void setVat(BigDecimal vat) {
		this.vat = vat;
	}
	public String getPreviousMeterRead1() {
		return previousMeterRead1;
	}
	public void setPreviousMeterRead1(String previousMeterRead1) {
		this.previousMeterRead1 = previousMeterRead1;
	}
	public String getPreviousMeterRead2() {
		return previousMeterRead2;
	}
	public void setPreviousMeterRead2(String previousMeterRead2) {
		this.previousMeterRead2 = previousMeterRead2;
	}
	public String getPreviousMeterRead3() {
		return previousMeterRead3;
	}
	public void setPreviousMeterRead3(String previousMeterRead3) {
		this.previousMeterRead3 = previousMeterRead3;
	}
	public String getCurrentMeterRead1() {
		return currentMeterRead1;
	}
	public void setCurrentMeterRead1(String currentMeterRead1) {
		this.currentMeterRead1 = currentMeterRead1;
	}
	public String getCurrentMeterRead2() {
		return currentMeterRead2;
	}
	public void setCurrentMeterRead2(String currentMeterRead2) {
		this.currentMeterRead2 = currentMeterRead2;
	}
	public String getCurrentMeterRead3() {
		return currentMeterRead3;
	}
	public void setCurrentMeterRead3(String currentMeterRead3) {
		this.currentMeterRead3 = currentMeterRead3;
	}
	
}
