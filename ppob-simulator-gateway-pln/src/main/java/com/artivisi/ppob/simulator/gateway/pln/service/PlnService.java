package com.artivisi.ppob.simulator.gateway.pln.service;

import com.artivisi.ppob.simulator.gateway.pln.entity.InquiryPostpaidResponse;

public interface PlnService {
	public void save(InquiryPostpaidResponse response);
	public InquiryPostpaidResponse findInquiryPostpaidResponse(String id);
}
