package com.artivisi.ppob.simulator.gateway.pln.service.impl;

import org.apache.commons.lang.StringUtils;
import org.hibernate.Hibernate;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.artivisi.ppob.simulator.gateway.pln.entity.InquiryPostpaidResponse;
import com.artivisi.ppob.simulator.gateway.pln.entity.InquiryPostpaidResponseDetail;
import com.artivisi.ppob.simulator.gateway.pln.service.PlnService;

@Service @Transactional
public class PlnServiceImpl implements PlnService {

	@Autowired private SessionFactory sessionFactory;
	
	@Override
	public void save(InquiryPostpaidResponse response) {
		sessionFactory.getCurrentSession().saveOrUpdate(response);
	}

	@Override
	public InquiryPostpaidResponse findInquiryPostpaidResponse(String id) {
		if(StringUtils.isEmpty(id)) return null;
		InquiryPostpaidResponse i = (InquiryPostpaidResponse) sessionFactory.getCurrentSession().get(InquiryPostpaidResponse.class, id);
		if(i!=null){
			for (InquiryPostpaidResponseDetail detail : i.getDetails()) {
				Hibernate.initialize(detail.getTagihanPascabayar().getPelanggan());
			}
		}
		return i;
	}

}
