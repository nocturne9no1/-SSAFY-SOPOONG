package com.sopoong.service;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.Random;

import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.sopoong.common.BaseMessage;
import com.sopoong.model.dto.ConfirmRequest;
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
	
	@Autowired
	private EmailService emailService;
	
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
		if(!member.get().getAuthNumber().equals("AUTH")) {
			resultMap.put("errors", "인증못받은 사용자");
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
	
	public BaseMessage sendEmail(String id) {
		Map<String,Object> resultMap = new HashMap<>();
		Optional<User> userOpt = userRepository.findByUserId(id);
		if(userOpt.isPresent()) {
			userOpt.get().setAuthNumber(generAuthKey());
			System.out.println(userOpt.get().getAuthNumber());
			emailService.sendMail(userOpt.get());
			userRepository.save(userOpt.get());
			resultMap.put("success", "이메일 보내기 완료");
		}else if(userOpt.get().getAuthNumber().equals("AUTH")) {
			resultMap.put("errors", "이미 인증된 아이디");
		}else {
			resultMap.put("errors", "존재하지 않는 아이디"); 
		}
		
		
		return new BaseMessage(HttpStatus.OK,resultMap);
	}
	//인증키 만들기 8글자
	public String generAuthKey() {
		StringBuffer temp = new StringBuffer();
		Random rnd = new Random();
		for (int i = 0; i < 8; i++) {
		    int rIndex = rnd.nextInt(3);
		    switch (rIndex) {
		    case 0:
		        // a-z
		        temp.append((char) ((int) (rnd.nextInt(26)) + 97));
		        break;
		    case 1:
		        // A-Z
		        temp.append((char) ((int) (rnd.nextInt(26)) + 65));
		        break;
		    case 2:
		        // 0-9
		        temp.append((rnd.nextInt(10)));
		        break;
		    }
		}
		
		return temp.toString();
	}

	public BaseMessage confirm(ConfirmRequest confirmRequest) {
		Map<String,Object> resultMap = new HashMap<>();
		Optional<User> userOpt = userRepository.findByUserId(confirmRequest.getId());
		if(userOpt.isPresent()) {
			if(userOpt.get().getAuthNumber().equals(confirmRequest.getAuthNumber())){
				userOpt.get().setAuthNumber("AUTH");
				userRepository.save(userOpt.get());
				resultMap.put("sucess", "인증 성공");
			}else {
				resultMap.put("errors", "인증번호 실패");
			}
		}else {
			resultMap.put("errors", "아이디 조회 실패");
		}
		return new BaseMessage(HttpStatus.OK,resultMap);
	}
}
