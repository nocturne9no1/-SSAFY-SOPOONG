package com.sopoong.controller;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.sopoong.common.BaseMessage;
import com.sopoong.service.GoodService;

import io.swagger.annotations.ApiOperation;

@RestController
@CrossOrigin(origins = { "*" })
public class GoodController {
	public static final Logger logger = LoggerFactory.getLogger(AuthController.class);
	@Autowired
	private GoodService goodService;
	

	@GetMapping("good/list")
	@ApiOperation("좋아요 누른사람 리스트 가져오기")
	public ResponseEntity getList(@RequestParam(required = true) final Long travelIdx, final Pageable pageable) throws Exception {
		BaseMessage bm = goodService.getList(pageable,travelIdx);
		return new ResponseEntity(new BaseMessage(bm.getHttpStatus(),bm.getData()),bm.getHeaders(),bm.getHttpStatus());

	}
}
