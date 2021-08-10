package com.sopoong.service;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.sopoong.common.BaseMessage;
import com.sopoong.model.entity.Good;
import com.sopoong.repository.GoodRepository;

@Service
public class GoodService {
	@Autowired
	private GoodRepository goodRepository;
	public static final Logger logger = LoggerFactory.getLogger(GoodService.class);
	@Transactional
	public BaseMessage getList(Pageable pageable, Long travelIdx) {
		Map<String,Object> resultMap = new HashMap<>();
		Page<Good> goods = goodRepository.findByTravel_TravelIdx(travelIdx, pageable);
		List<String> people = new ArrayList<>();
		for(int i=0;i<goods.getContent().size();i++) {
			people.add(goods.getContent().get(i).getUser().getUserNickname());
		}
		resultMap.put("seccess", people);
		resultMap.put("isLast", goods.isLast());
		return new BaseMessage(HttpStatus.OK,resultMap); 
	}
}
