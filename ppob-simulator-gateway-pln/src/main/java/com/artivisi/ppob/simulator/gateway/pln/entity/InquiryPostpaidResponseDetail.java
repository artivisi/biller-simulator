package com.artivisi.ppob.simulator.gateway.pln.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

import com.artivisi.ppob.simulator.entity.TagihanPascabayar;

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
