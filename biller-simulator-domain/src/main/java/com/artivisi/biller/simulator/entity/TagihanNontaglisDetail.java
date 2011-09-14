package com.artivisi.biller.simulator.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

@Entity @Table(name="tagihan_nontaglis_detail")
public class TagihanNontaglisDetail {

	@Id @GeneratedValue(generator = "system-uuid")
    @GenericGenerator(name = "system-uuid", strategy = "uuid")
	private String id ;
	@Column(name="total_repeat", nullable=false)
	private Integer totalRepeat ;
	@Column(name="detail_code", nullable=false)
	private Integer customDetailCode ;
	@Column(name="detail_unit", nullable=false)
	private Integer customDetailUnit ;
	@Column(name="detail_value_amount", nullable=false)
	private Integer customDetailValueAmount ;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public Integer getTotalRepeat() {
		return totalRepeat;
	}
	public void setTotalRepeat(Integer totalRepeat) {
		this.totalRepeat = totalRepeat;
	}
	public Integer getCustomDetailCode() {
		return customDetailCode;
	}
	public void setCustomDetailCode(Integer customDetailCode) {
		this.customDetailCode = customDetailCode;
	}
	public Integer getCustomDetailUnit() {
		return customDetailUnit;
	}
	public void setCustomDetailUnit(Integer customDetailUnit) {
		this.customDetailUnit = customDetailUnit;
	}
	public Integer getCustomDetailValueAmount() {
		return customDetailValueAmount;
	}
	public void setCustomDetailValueAmount(Integer customDetailValueAmount) {
		this.customDetailValueAmount = customDetailValueAmount;
	}
	
	
}
