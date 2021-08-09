package com.sopoong.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.sopoong.common.BaseMessage;
import com.sopoong.model.dto.changeAlarmRequest;
import com.sopoong.model.dto.changePasswordRequest;
import com.sopoong.model.dto.changeProfileRequest;
import com.sopoong.model.dto.changeVisibleRequest;
import com.sopoong.service.UserService;

import io.swagger.annotations.ApiOperation;

@RestController
public class UserController {
	
	@Autowired
	private UserService userService;
	
	@GetMapping("/user")
	@ApiOperation(value = "프로필 가져오기")
	public ResponseEntity<BaseMessage> getProfile(@RequestParam String id) throws Exception {
		
		BaseMessage bm= userService.getProfile(id);
		return new ResponseEntity<BaseMessage>(new BaseMessage(bm.getHttpStatus(), bm.getData()), bm.getHeaders(), bm.getHttpStatus());
		
	}
	
	@PatchMapping("/user/profile")
	@ApiOperation(value = "프로필 변경하기")
	public ResponseEntity<BaseMessage> changeProfile(@Valid @RequestBody changeProfileRequest request) {
		
		BaseMessage bm= userService.changeProfile(request);
		return new ResponseEntity<BaseMessage>(new BaseMessage(bm.getHttpStatus(), bm.getData()), bm.getHeaders(), bm.getHttpStatus());
		
	}
	
	@PatchMapping("/user/password")
	@ApiOperation(value = "비밀번호 변경하기")
	public ResponseEntity<BaseMessage> changePassword(@Valid @RequestBody changePasswordRequest request) {
		
		BaseMessage bm= userService.changePassword(request);
		return new ResponseEntity<BaseMessage>(new BaseMessage(bm.getHttpStatus(), bm.getData()), bm.getHeaders(), bm.getHttpStatus());
		
	}
	
	@PatchMapping("/user/alarm")
	@ApiOperation(value = "알람 범위 변경하기")
	public ResponseEntity<BaseMessage> changeAlarm(@Valid @RequestBody changeAlarmRequest request) {
		
		BaseMessage bm= userService.changeAlarm(request);
		return new ResponseEntity<BaseMessage>(new BaseMessage(bm.getHttpStatus(), bm.getData()), bm.getHeaders(), bm.getHttpStatus());
		
	}
	
	@PatchMapping("/user/visible")
	@ApiOperation(value = "계정 공개범위 변경하기")
	public ResponseEntity<BaseMessage> changeVisible(@Valid @RequestBody changeVisibleRequest request) {
		
		BaseMessage bm= userService.changeVisible(request);
		return new ResponseEntity<BaseMessage>(new BaseMessage(bm.getHttpStatus(), bm.getData()), bm.getHeaders(), bm.getHttpStatus());
		
	}
	
	@DeleteMapping("/user/delete")
	@ApiOperation(value = "계정 삭제하기")
	public ResponseEntity<BaseMessage> deleteUser(@RequestParam String id) {
		
		BaseMessage bm= userService.deleteUser(id);
		return new ResponseEntity<BaseMessage>(new BaseMessage(bm.getHttpStatus(), bm.getData()), bm.getHeaders(), bm.getHttpStatus());
		
	}
	
}
