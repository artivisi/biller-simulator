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

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

@Entity @Table(name="token_unsold")
public class TokenUnsold {
	@Id @GeneratedValue(generator = "system-uuid")
    @GenericGenerator(name = "system-uuid", strategy = "uuid")
	private String id;

	@ManyToOne @JoinColumn(name="id_pelanggan", nullable=false)
	private Pelanggan pelanggan;
	
	@Column(nullable=false, name="nilai_token")
	private BigDecimal nilaiToken;
	@Column(nullable=false, name="nomer_token")
	private String nomerToken;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public Pelanggan getPelanggan() {
		return pelanggan;
	}
	public void setPelanggan(Pelanggan pelanggan) {
		this.pelanggan = pelanggan;
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
	
	
}
