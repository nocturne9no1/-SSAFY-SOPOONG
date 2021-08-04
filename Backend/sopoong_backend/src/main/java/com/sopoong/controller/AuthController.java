package com.sopoong.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.sopoong.common.BaseMessage;
import com.sopoong.model.dto.SignupRequest;
import com.sopoong.service.AuthService;

import io.swagger.annotations.ApiOperation;

@RestController
public class AuthController {
	public static final Logger logger = LoggerFactory.getLogger(AuthController.class);
	@Autowired
	private AuthService authService;
	

	@GetMapping("auth/login")
	@ApiOperation("로그인")
	public ResponseEntity login(@RequestParam(required = true) final String id,
            @RequestParam(required = true) final String password) throws Exception {
		BaseMessage bm = authService.login(id,password);
		return new ResponseEntity(new BaseMessage(bm.getHttpStatus(),bm.getData()),bm.getHeaders(),bm.getHttpStatus());

	}

	@PostMapping("auth/register")
	@ApiOperation("회원가입")
	public ResponseEntity join(@RequestBody SignupRequest requestUser) {
		BaseMessage bm = authService.joinUser(requestUser); 
		return new ResponseEntity(new BaseMessage(bm.getHttpStatus(),bm.getData()),bm.getHeaders(),bm.getHttpStatus());
	}
	
	@GetMapping("auth/id")
	@ApiOperation("아이디 중복 검사")
	public ResponseEntity checkId(@RequestParam(required = true) final String id) throws Exception{
		BaseMessage bm = authService.checkId(id);
		return new ResponseEntity(new BaseMessage(bm.getHttpStatus(),bm.getData()),bm.getHeaders(),bm.getHttpStatus());
	}
	
	@GetMapping("auth/email")
	@ApiOperation("이메일 중복 검사")
	public ResponseEntity checkEmail(@RequestParam(required = true) final String email) throws Exception{
		BaseMessage bm = authService.checkEmail(email);
		return new ResponseEntity(new BaseMessage(bm.getHttpStatus(),bm.getData()),bm.getHeaders(),bm.getHttpStatus());
	}
	
	@GetMapping("auth/nickname")
	@ApiOperation("닉네임 중복 검사")
	public ResponseEntity checkNickname(@RequestParam(required = true) final String nickName) throws Exception{
		BaseMessage bm = authService.checkNickname(nickName);
		return new ResponseEntity(new BaseMessage(bm.getHttpStatus(),bm.getData()),bm.getHeaders(),bm.getHttpStatus());
	}
}
