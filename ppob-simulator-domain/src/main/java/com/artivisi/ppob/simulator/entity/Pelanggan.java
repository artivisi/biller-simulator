package com.artivisi.ppob.simulator.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.validator.constraints.NotEmpty;

@Entity @Table(name="m_pelanggan")
public class Pelanggan {
	
	@Id @GeneratedValue(generator = "system-uuid")
    @GenericGenerator(name = "system-uuid", strategy = "uuid")
	private String id;

	@NotNull
	@NotEmpty
	@Column(nullable=false, unique=true)
	private String idpel;
	@NotNull
	@NotEmpty
	@Column(name="meter_number", nullable=false, unique=true)
	private String meterNumber;
	@NotNull
	@NotEmpty
	@Column(nullable=false)
	private String nama;
	
	@Column(name="service_unit")
	private String serviceUnit;
	@Column(name="service_unit_phone")
	private String serviceUnitPhone;
	@Column(name="subscriber_segmentation")
	private String subscriberSegmentation;
	@Column(name="power_consuming_category")
	private String powerConsumingCategory;
	
	@NotNull
	@NotEmpty
	@Column(name="response_code", nullable=false)
	private String responseCode = "0000";
	
	@NotNull
	@Column(name="hold_response", nullable=false)
	private Integer holdResponse = 0;
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getIdpel() {
		return idpel;
	}
	public String getMeterNumber() {
		return meterNumber;
	}
	public void setMeterNumber(String meterNumber) {
		this.meterNumber = meterNumber;
	}
	public void setIdpel(String idpel) {
		this.idpel = idpel;
	}
	public String getNama() {
		return nama;
	}
	public void setNama(String nama) {
		this.nama = nama;
	}
	public String getServiceUnit() {
		return serviceUnit;
	}
	public void setServiceUnit(String serviceUnit) {
		this.serviceUnit = serviceUnit;
	}
	public String getServiceUnitPhone() {
		return serviceUnitPhone;
	}
	public void setServiceUnitPhone(String serviceUnitPhone) {
		this.serviceUnitPhone = serviceUnitPhone;
	}
	public String getSubscriberSegmentation() {
		return subscriberSegmentation;
	}
	public void setSubscriberSegmentation(String subscriberSegmentation) {
		this.subscriberSegmentation = subscriberSegmentation;
	}
	public String getPowerConsumingCategory() {
		return powerConsumingCategory;
	}
	public void setPowerConsumingCategory(String powerConsumingCategory) {
		this.powerConsumingCategory = powerConsumingCategory;
	}
	public String getResponseCode() {
		return responseCode;
	}
	public void setResponseCode(String responseCode) {
		this.responseCode = responseCode;
	}
	public Integer getHoldResponse() {
		return holdResponse;
	}
	public void setHoldResponse(Integer holdResponse) {
		this.holdResponse = holdResponse;
	}
}
