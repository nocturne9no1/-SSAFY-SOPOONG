package com.sopoong.service;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.sopoong.common.BaseMessage;
import com.sopoong.model.dto.followRequest;
import com.sopoong.model.entity.Relation;
import com.sopoong.repository.RelationRepository;
import com.sopoong.repository.UserRepository;

@Service
public class RelationService {
	
	@Autowired
	private RelationRepository relationRepo;
	@Autowired
	private UserRepository userRepo;
	
	@Transactional
	public BaseMessage follow(followRequest request) {
		
		Map<String,Object> resultMap= new HashMap<>();
		Optional<Relation> relation= relationRepo.findByRelationFollowingAndRelationFollowed(userRepo.findByUserId(request.getRelationFollowing()).get(), userRepo.findByUserId(request.getRelationFollowed()).get());
		
		if (relation.isPresent()) {
			relationRepo.delete(relation.get());
			resultMap.put("success", "팔로우 취소");
			return new BaseMessage(HttpStatus.OK, resultMap);
		} else {
			relationRepo.save(Relation.builder()
					.relationFollowing(userRepo.findByUserId(request.getRelationFollowing()).get())
					.relationFollowed(userRepo.findByUserId(request.getRelationFollowed()).get())
					.build());
			System.out.println(userRepo.findByUserId(request.getRelationFollowing()).get().toString());
			System.out.println(userRepo.findByUserId(request.getRelationFollowed()).get().toString());
			resultMap.put("success", "팔로우");
			return new BaseMessage(HttpStatus.OK, resultMap);
		}
		
	}
	
}
