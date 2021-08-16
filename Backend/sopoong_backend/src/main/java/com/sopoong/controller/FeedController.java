package com.sopoong.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.sopoong.common.BaseMessage;
import com.sopoong.service.FeedService;

import io.swagger.annotations.ApiOperation;

@RestController
@CrossOrigin(origins = { "*" })
@RequestMapping("/api")
public class FeedController {
	public static final Logger logger = LoggerFactory.getLogger(AuthController.class);
	@Autowired
	private FeedService feedService;
	
	@GetMapping("feed/list")
	@ApiOperation("좋아요 누른사람 리스트 가져오기")
	public ResponseEntity getList(@RequestParam(required = true) final String userId, @PageableDefault(sort = {"createdAt"},direction = Direction.DESC) Pageable pageable) throws Exception {
		BaseMessage bm = feedService.getList(userId,pageable);
		return new ResponseEntity(new BaseMessage(bm.getHttpStatus(),bm.getData()),bm.getHeaders(),bm.getHttpStatus());

	}
}
