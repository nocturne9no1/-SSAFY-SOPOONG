package com.sopoong.controller;

import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.sopoong.model.dto.changeAlarmRequest;
import com.sopoong.model.dto.changePasswordRequest;
import com.sopoong.model.dto.changeProfileRequest;
import com.sopoong.model.dto.changeVisibleRequest;
import com.sopoong.model.dto.getProfileRequest;
import com.sopoong.model.entity.User;
import com.sopoong.repository.UserRepository;

import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping("/index")
public class UserController {
	
	@Autowired
	UserRepository userRepo;
	
	@GetMapping("/user")
	@ApiOperation(value = "프로필 가져오기")
	public getProfileRequest getProfile(@RequestParam String id) {
		
		Optional<User> user= userRepo.findById(id);
		getProfileRequest getUser= new getProfileRequest();
		
		getUser.setUserId(user.get().getUserId());
		getUser.setUserNickname(user.get().getUserNickname());
		getUser.setUserComment(user.get().getUserComment());
		getUser.setImage(user.get().getImage());
		getUser.setFollowings(user.get().getRelationFollowing().size());
		getUser.setFollowers(user.get().getRelationFollowed().size());
		
		return getUser;
		
	}
	
	@PatchMapping("/user/profile")
	@ApiOperation(value = "프로필 변경하기")
	public Optional<User> changeProfile(@Valid @RequestBody changeProfileRequest request) {
		
		Optional<User> updateUser= userRepo.findById(request.getUserId());
		
		updateUser.ifPresent(selectUser->{
			selectUser.setImage(request.getImage());
			selectUser.setUserNickname(request.getUserNickname());
			selectUser.setUserComment(request.getUserComment());
			
			userRepo.save(selectUser);
		});
		
		return updateUser;
		
	}
	
	@PatchMapping("/user/password")
	@ApiOperation(value = "비밀번호 변경하기")
	public Optional<User> changePassword(@Valid @RequestBody changePasswordRequest request) {
		
		Optional<User> updateUser= userRepo.findUserByUserEmailAndUserPassword(request.getUserEmail(), request.getUserPassword());
		
		updateUser.ifPresent(selectUser->{
			selectUser.setUserPassword(request.getChangedPassword());
			
			userRepo.save(selectUser);
		});
		
		return updateUser;
		
	}
	
	@PatchMapping("/user/alarm")
	@ApiOperation(value = "알람 범위 변경하기")
	public Optional<User> changeAlarm(@Valid @RequestBody changeAlarmRequest request) {
		
		Optional<User> updateUser= userRepo.findById(request.getUserId());
		
		updateUser.ifPresent(selectUser->{
			selectUser.setUserAlarm(request.getUserAlarm());
			
			userRepo.save(selectUser);
		});
		
		return updateUser;
		
	}
	
	@PatchMapping("/user/visible")
	@ApiOperation(value = "계정 공개범위 변경하기")
	public Optional<User> changeVisible(@Valid @RequestBody changeVisibleRequest request) {
		
		Optional<User> updateUser= userRepo.findById(request.getUserId());
		
		updateUser.ifPresent(selectUser->{
			selectUser.setUserIsVisible(request.getUserIsVisible());
			
			userRepo.save(selectUser);
		});
		
		return updateUser;
		
	}
	
	@DeleteMapping("/user/delete")
	@ApiOperation(value = "계정 삭제하기")
	public void deleteUser(@RequestParam String id) {
		
		Optional<User> deleteUser= userRepo.findById(id);
		
		deleteUser.ifPresent(selectUser->{
			userRepo.delete(selectUser);
		});
		
	}
	
}
