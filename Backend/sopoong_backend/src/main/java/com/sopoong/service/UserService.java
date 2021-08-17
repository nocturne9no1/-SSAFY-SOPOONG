package com.sopoong.service;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.sopoong.common.BaseMessage;
import com.sopoong.model.dto.changeAlarmRequest;
import com.sopoong.model.dto.changePasswordRequest;
import com.sopoong.model.dto.changeProfileRequest;
import com.sopoong.model.dto.changeVisibleRequest;
import com.sopoong.model.dto.getProfileRequest;
import com.sopoong.model.entity.User;
import com.sopoong.repository.UserRepository;

@Service
public class UserService {
	
	@Autowired
	private UserRepository userRepo;
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	public BaseMessage getProfile(String id) {
		
		Map<String,Object> resultMap= new HashMap<>();
		Optional<User> user= userRepo.findByUserId(id);
		getProfileRequest getUser= new getProfileRequest();
		
		if (user.isPresent()) {
			getUser.setUserId(user.get().getUserId());
			getUser.setUserNickname(user.get().getUserNickname());
			getUser.setUserIsVisible(user.get().getUserIsVisible());
			getUser.setUserComment(user.get().getUserComment());
			getUser.setImage(user.get().getImage());
			getUser.setFollowings(user.get().getRelationFollowing().size());
			getUser.setFollowers(user.get().getRelationFollowed().size());
			resultMap.put("success", getUser);
			return new BaseMessage(HttpStatus.OK, resultMap);
		} else {
			resultMap.put("errors", "존재하지 않는 아이디");
			return new BaseMessage(HttpStatus.BAD_REQUEST, resultMap);
		}
		
	}

	public BaseMessage changeProfile(changeProfileRequest request) {
		
		Map<String,Object> resultMap= new HashMap<>();
		Optional<User> updateUser= userRepo.findByUserId(request.getUserId());
		
		if (updateUser.isPresent()) {
			updateUser.get().setImage(request.getImage());
			updateUser.get().setUserNickname(request.getUserNickname());
			updateUser.get().setUserComment(request.getUserComment());
			userRepo.save(updateUser.get());
			
			resultMap.put("success", "프로필 변경 성공");
			return new BaseMessage(HttpStatus.OK, resultMap);
		} else {
			resultMap.put("errors", "프로필 변경 실패 (존재하지 않는 아이디)");
			return new BaseMessage(HttpStatus.BAD_REQUEST, resultMap);
		}
		
	}
	
	public BaseMessage changePassword(changePasswordRequest request) {
		
		Map<String,Object> resultMap= new HashMap<>();
		Optional<User> updateUser= userRepo.findByUserId(request.getUserId());
		
		if (updateUser.isPresent()) {
			if (updateUser.get().getUserPassword()==request.getUserPassword()) {
				updateUser.get().setUserPassword(passwordEncoder.encode(request.getChangedPassword()));
				userRepo.save(updateUser.get());
				
				resultMap.put("success", "비밀번호 변경 성공");	
			} else {
				resultMap.put("errors", "비밀번호 일치하지 않음");
			}
			return new BaseMessage(HttpStatus.OK, resultMap);
		} else {
			resultMap.put("errors", "비밀번호 변경 실패");
			return new BaseMessage(HttpStatus.BAD_REQUEST, resultMap);
		}
		
	}
	
	public BaseMessage changeAlarm(changeAlarmRequest request) {
		
		Map<String,Object> resultMap= new HashMap<>();
		Optional<User> updateUser= userRepo.findByUserId(request.getUserId());
		
		if (updateUser.isPresent()) {
			updateUser.get().setUserAlarm(request.getUserAlarm());
			userRepo.save(updateUser.get());
			
			resultMap.put("success", "알람 범위 변경 성공");
			return new BaseMessage(HttpStatus.OK, resultMap);
		} else {
			resultMap.put("errors", "알람 범위 변경 실패 (존재하지 않는 아이디)");
			return new BaseMessage(HttpStatus.BAD_REQUEST, resultMap);
		}
		
	}
	
	public BaseMessage changeVisible(changeVisibleRequest request) {
		
		Map<String,Object> resultMap= new HashMap<>();
		Optional<User> updateUser= userRepo.findByUserId(request.getUserId());
		
		if (updateUser.isPresent()) {
			updateUser.get().setUserIsVisible(request.getUserIsVisible());
			userRepo.save(updateUser.get());
			
			resultMap.put("success", "계정 공개 범위 변경 성공");
			return new BaseMessage(HttpStatus.OK, resultMap);
		} else {
			resultMap.put("errors", "계정 공개 범위 변경 실패 (존재하지 않는 아이디)");
			return new BaseMessage(HttpStatus.BAD_REQUEST, resultMap);
		}
		
	}
	
	public BaseMessage deleteUser(String id) {
		
		Map<String,Object> resultMap= new HashMap<>();
		Optional<User> deleteUser= userRepo.findByUserId(id);

		
		if (deleteUser.isPresent()) {
			userRepo.delete(deleteUser.get());
			
			resultMap.put("success", "계정 삭제 성공");
			return new BaseMessage(HttpStatus.OK, resultMap);
		} else {
			resultMap.put("errors", "계정 삭제 실패 (존재하지 않는 아이디)");
			return new BaseMessage(HttpStatus.BAD_REQUEST, resultMap);
		}
		
	}
	
}
