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
package com.artivisi.biller.simulator.gateway.pln.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

import com.artivisi.biller.simulator.entity.TagihanPascabayar;

@Entity @Table(name="inquiry_postpaid_response_detail")
public class InquiryPostpaidResponseDetail {
	@Id @GeneratedValue(generator = "system-uuid")
    @GenericGenerator(name = "system-uuid", strategy = "uuid")
	private String id;
	
	@ManyToOne
	@JoinColumn(name="id_inquiry_postpaid_response", nullable=false)
	private InquiryPostpaidResponse inquiryPostpaidResponse;
	
	@ManyToOne
	@JoinColumn(name="id_tagihan_pascabayar", nullable=false)
	private TagihanPascabayar tagihanPascabayar;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public InquiryPostpaidResponse getInquiryPostpaidResponse() {
		return inquiryPostpaidResponse;
	}

	public void setInquiryPostpaidResponse(
			InquiryPostpaidResponse inquiryPostpaidResponse) {
		this.inquiryPostpaidResponse = inquiryPostpaidResponse;
	}

	public TagihanPascabayar getTagihanPascabayar() {
		return tagihanPascabayar;
	}

	public void setTagihanPascabayar(TagihanPascabayar tagihanPascabayar) {
		this.tagihanPascabayar = tagihanPascabayar;
	}
	
	
}
