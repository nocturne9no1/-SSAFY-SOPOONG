package com.sopoong.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

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
	// 방법 1. @ModelAttribute  t-value(.append 써서)
	// 방법 2. @RequestPar Dto, imageList
	public ResponseEntity<BaseMessage> writeTravel(@ModelAttribute TravelDto travelDto) throws Exception {
		BaseMessage bm = travelService.writeTravel(travelDto);
		System.out.println("[controller] " + bm.getHttpStatus());
		System.out.println("끝");
		return new ResponseEntity<BaseMessage>(new BaseMessage(bm.getHttpStatus(), bm.getData()), bm.getHeaders(), bm.getHttpStatus());
	}
}
