package com.sopoong.service;

import java.io.File;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;
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
import com.sopoong.model.entity.Image;
import com.sopoong.model.entity.User;
import com.sopoong.repository.ImageRepository;
import com.sopoong.repository.UserRepository;

@Service
public class UserService {
	
	@Autowired
	private ImageService imageService;
	@Autowired
	private ImageRepository imageRepo;
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

	public BaseMessage changeProfile(changeProfileRequest request) throws IllegalStateException, NoSuchAlgorithmException, IOException {
		
		Map<String,Object> resultMap= new HashMap<>();
		Optional<User> updateUser= userRepo.findByUserId(request.getUserId());
		
		long imageIdx = updateUser.get().getImage().getImageIdx();
		if(imageIdx != 0) { // 프로필 사진이 있는 경우
			// 파일 제거
			Optional<Image> image = imageRepo.findByImageIdx(imageIdx);
			String path = image.get().getImagePath();
			File file = new File(path);
			
			if(file.delete()){ // 파일 삭제에 성공하면 true, 실패하면 false
                System.out.println("파일을 삭제하였습니다");
            }else{
                System.out.println("파일 삭제에 실패하였습니다");
            }
			
			// DB에서 제거
			imageService.deleteImage(imageIdx);
		}
		
		// 질문? 프로필 사진을 지운경우, image_idx에 0을 넣어야되는데,,, Image로 되어있어서 불가능...
		// 어떻게 해야되나..?
		if(request.getImage().isEmpty()) { // 프로필 사진이 없는 경우
			
		}
		
		BaseMessage bm = imageService.saveProfile(request.getImage(), request.getUserComment());
		long imgIdx = (long) bm.getData();
		
		if (updateUser.isPresent()) {
			updateUser.get().setImage(imageRepo.findByImageIdx(imgIdx).get());
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
			updateUser.ifPresent(selectUser->{
				selectUser.setUserPassword(passwordEncoder.encode(request.getChangedPassword()));
				
				userRepo.save(selectUser);
			});
			
			resultMap.put("success", "비밀번호 변경 성공");
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
			updateUser.ifPresent(selectUser->{
				selectUser.setUserAlarm(request.getUserAlarm());
				
				userRepo.save(selectUser);
			});
			
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
			updateUser.ifPresent(selectUser->{
				selectUser.setUserIsVisible(request.getUserIsVisible());
				
				userRepo.save(selectUser);
			});
			
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
			deleteUser.ifPresent(selectUser->{
				userRepo.delete(selectUser);
			});
			resultMap.put("success", "계정 삭제 성공");
			return new BaseMessage(HttpStatus.OK, resultMap);
		} else {
			resultMap.put("errors", "계정 삭제 실패 (존재하지 않는 아이디)");
			return new BaseMessage(HttpStatus.BAD_REQUEST, resultMap);
		}
		
	}
	
}
