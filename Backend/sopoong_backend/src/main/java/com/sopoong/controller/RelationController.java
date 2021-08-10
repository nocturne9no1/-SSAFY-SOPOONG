package com.sopoong.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sopoong.common.BaseMessage;
import com.sopoong.model.dto.followRequest;
import com.sopoong.model.entity.Relation;
import com.sopoong.service.RelationService;

@RestController
@RequestMapping("/auth")
public class RelationController {
	
	@Autowired
	private RelationService relationService;
	
	@PostMapping("follow")
	public ResponseEntity<Relation> follow(@RequestBody followRequest request) throws Exception {
		
		BaseMessage bm= relationService.follow(request);
		return new ResponseEntity(new BaseMessage(bm.getHttpStatus(),bm.getData()),bm.getHeaders(),bm.getHttpStatus());
		
	}
	
}
