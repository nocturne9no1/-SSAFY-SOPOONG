package com.sopoong.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.sopoong.common.BaseMessage;
import com.sopoong.model.dto.FeedDto;
import com.sopoong.model.entity.Relation;
import com.sopoong.model.entity.Travel;
import com.sopoong.model.entity.User;
import com.sopoong.repository.TravelRepository;
import com.sopoong.repository.UserRepository;

@Service
public class FeedService {

	@Autowired
	private UserRepository userRepository;
	@Autowired
	private TravelRepository travelRepository;
	
	public BaseMessage getList(String id, Pageable pageable) {
		Map<String,Object> resultMap = new HashMap<>();
		System.out.println(pageable.getSort());
		Optional<User> optUsers = userRepository.findByUserId(id);
		List<Relation> followings = new ArrayList<>();
		if(optUsers.isPresent()) {
			followings = optUsers.get().getRelationFollowing();
		}else {
			resultMap.put("errors", "존재하지 않는 아이디");
			return new BaseMessage(HttpStatus.BAD_REQUEST,resultMap);
		}
		
		List<String> userIds = new ArrayList<>();
		for(int i=0;i<followings.size();i++) {
			userIds.add(followings.get(i).getRelationFollowed().getUserId());
		}
		
		Page<Travel> travels = travelRepository.findByUser_UserIdInAndTravelIsVisible(userIds, 1,pageable);
		List<FeedDto> feeds = new ArrayList<>();
		for(int i=0;i<travels.getContent().size();i++) {
			feeds.add(new FeedDto(travels.getContent().get(i)));
		}
		resultMap.put("success", feeds);
		resultMap.put("isLast", travels.isLast());
		return new BaseMessage(HttpStatus.OK,resultMap);
	}

}
