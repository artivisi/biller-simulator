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
package com.artivisi.biller.simulator.gateway.pln.service.impl;

import org.apache.commons.lang.StringUtils;
import org.hibernate.Hibernate;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.artivisi.biller.simulator.gateway.pln.entity.InquiryPostpaidResponse;
import com.artivisi.biller.simulator.gateway.pln.entity.InquiryPostpaidResponseDetail;
import com.artivisi.biller.simulator.gateway.pln.service.PlnService;

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
