package com.artivisi.ppob.simulator.gateway.pln.entity;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

@Entity @Table(name="inquiry_postpaid_response")
public class InquiryPostpaidResponse {
	
	@Id @GeneratedValue(generator = "system-uuid")
    @GenericGenerator(name = "system-uuid", strategy = "uuid")
	private String id;
	
	@Column(nullable=false)
	private String switcher;
	@Column(nullable=false)
	private String bank;
	@Column(nullable=false)
	private String stan;
	
	@OneToMany(mappedBy="inquiryPostpaidResponse", cascade=CascadeType.ALL, orphanRemoval=true)
	private List<InquiryPostpaidResponseDetail> details = new ArrayList<InquiryPostpaidResponseDetail>();
	
	public List<InquiryPostpaidResponseDetail> getDetails() {
		return details;
	}
	public void setDetails(List<InquiryPostpaidResponseDetail> details) {
		this.details = details;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getSwitcher() {
		return switcher;
	}
	public void setSwitcher(String switcher) {
		this.switcher = switcher;
	}
	public String getBank() {
		return bank;
	}
	public void setBank(String bank) {
		this.bank = bank;
	}
	public String getStan() {
		return stan;
	}
	public void setStan(String stan) {
		this.stan = stan;
	}
}
