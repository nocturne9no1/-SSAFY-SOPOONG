package com.sopoong.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sopoong.common.BaseMessage;
import com.sopoong.model.dto.TravelDto;
import com.sopoong.service.TravelService;

import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = { "*" })
public class TravelController {
	public static final Logger logger = LoggerFactory.getLogger(AuthController.class);
	
	@Autowired
	private TravelService travelService;
	
	@ApiOperation(value = "여행 일지 작성")
	@PostMapping("/travel/create")
	public ResponseEntity<BaseMessage> writeTravel(@ModelAttribute TravelDto travelDto) throws Exception {
		BaseMessage bm = travelService.writeTravel(travelDto);
		return new ResponseEntity<BaseMessage>(new BaseMessage(bm.getHttpStatus(), bm.getData()), bm.getHeaders(), bm.getHttpStatus());
	}
}
