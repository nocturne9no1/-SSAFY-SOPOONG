package com.sopoong.service;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.sopoong.common.BaseMessage;
import com.sopoong.model.dto.SignupRequest;
import com.sopoong.model.entity.User;
import com.sopoong.repository.UserRepository;
import com.sopoong.util.JwtTokenProvider;

@Service
public class AuthService {
	public static final Logger logger = LoggerFactory.getLogger(AuthService.class);
	@Autowired
	private UserRepository userRepository;
	@Autowired
	private PasswordEncoder passwordEncoder;
	@Autowired
	private JwtTokenProvider jwtTokenProvider;
	@Transactional
	public BaseMessage joinUser(SignupRequest requestUser) {
		Map<String,Object> resultMap = new HashMap<>();
		userRepository.save(User.builder().userId(requestUser.getId()).
				userEmail(requestUser.getEmail())
				.userPassword(passwordEncoder.encode(requestUser.getPassword())).
				userNickname(requestUser.getNickname())// 최초.																									// 설정
				.build());
		
		resultMap.put("seccess", "회원가입 성공");
		return new BaseMessage(HttpStatus.OK,resultMap); 
	}
	
	@Transactional
	public BaseMessage login(String id, String password){
		Map<String,Object> resultMap = new HashMap<>();
		Optional<User> member = userRepository.findByUserId(id);
		if(!member.isPresent()) {
			resultMap.put("errors","존재하지 않는 아이디");
			return new BaseMessage(HttpStatus.BAD_REQUEST,resultMap);
		}
		if (!passwordEncoder.matches(password, member.get().getUserPassword())) {
			resultMap.put("errors","비밀번호 틀림");
			return new BaseMessage(HttpStatus.BAD_REQUEST,resultMap);
		}
		resultMap.put("seccess", jwtTokenProvider.createToken(member.get().getUserId()));
		return new BaseMessage(HttpStatus.OK,resultMap); 
	}
	
	@Transactional
	public BaseMessage checkId(String id) {
		Map<String,Object> resultMap = new HashMap<>();
		if(userRepository.findByUserId(id).isPresent()) {
			resultMap.put("errors", "아이디 중복");
		}else {
			resultMap.put("success", "아이디 중복검사 통과");
		}
		return new BaseMessage(HttpStatus.OK,resultMap);
	}
	
	@Transactional
	public BaseMessage checkEmail(String email) {
		Map<String,Object> resultMap = new HashMap<>();
		if(userRepository.findByUserEmail(email).isPresent()) {
			resultMap.put("errors", "이메일 중복");
		}else {
			resultMap.put("success", "이메일 중복검사 통과");
		}
		return new BaseMessage(HttpStatus.OK,resultMap);
	}
	
	@Transactional
	public BaseMessage checkNickname(String nickName) {
		Map<String,Object> resultMap = new HashMap<>();
		if(userRepository.findByUserNickname(nickName).isPresent()) {
			resultMap.put("errors", "닉네임 중복");
		}else {
			resultMap.put("success", "닉네임 중복검사 통과");
		}
		return new BaseMessage(HttpStatus.OK,resultMap);
	}
	
}
