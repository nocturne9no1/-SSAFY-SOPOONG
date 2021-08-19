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
import com.sopoong.model.dto.TravelList;
import com.sopoong.model.entity.Relation;
import com.sopoong.model.entity.Travel;
import com.sopoong.model.entity.User;
import com.sopoong.repository.GoodRepository;
import com.sopoong.repository.RelationRepository;
import com.sopoong.repository.TravelRepository;
import com.sopoong.repository.UserRepository;

@Service
public class FeedService {

	@Autowired
	private UserRepository userRepository;
	@Autowired
	private TravelRepository travelRepository;
	
	@Autowired
	private GoodRepository goodRepository;
	
	@Autowired
	private RelationRepository relationRepository;
	
	public BaseMessage getFollowList(String id, Pageable pageable) {
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
		List<TravelList> feeds = new ArrayList<>();
		for(Travel travel : travels) {
			TravelList t = TravelList.builder()
					.travelIdx(travel.getTravelIdx())
					.travelTitle(travel.getTravelTitle())
					.travelContent(travel.getTravelContent())
					.imageOriginTitle(travel.getImage().getImageOriginTitle())
					.profileOriginTitle(travel.getUser().getImage().getImageOriginTitle())
					.travelLat(travel.getTravelLat())
					.travelLong(travel.getTravelLong())
					.startDate(travel.getStartDate())
					.endDate(travel.getEndDate())
					.imageWidth(travel.getImage().getImageWidth())
					.imageHeight(travel.getImage().getImageHeight())
					.totalLike(goodRepository.countByTravel_TravelIdx(travel.getTravelIdx()))
					.userId(travel.getUser().getUserId())
					.userNickname(travel.getUser().getUserNickname())
					.build();
			if(goodRepository.findByUser_UserIdAndTravel_TravelIdx(id, travel.getTravelIdx()).isPresent()) t.setIsLike(1);
			else t.setIsLike(0);
			
			if(relationRepository.findByRelationFollowingAndRelationFollowed(userRepository.findByUserId(id).get(),travel.getUser()).isPresent()) {
				t.setIsFollow(1);
			}else {
				t.setIsFollow(0);
			}
			
			feeds.add(t);
		}
		resultMap.put("success", feeds);
		resultMap.put("isLast", travels.isLast());
		return new BaseMessage(HttpStatus.OK,resultMap);
	}

	public BaseMessage getAllList(String userId, Pageable pageable) {
		Map<String,Object> resultMap = new HashMap<>();
		
		Page<Travel> travels = travelRepository.findAll(pageable);
		List<TravelList> feeds = new ArrayList<>();
		for(Travel travel : travels) {
			TravelList t = TravelList.builder()
					.travelIdx(travel.getTravelIdx())
					.travelTitle(travel.getTravelTitle())
					.travelContent(travel.getTravelContent())
					.imageOriginTitle(travel.getImage().getImageOriginTitle())
					.profileOriginTitle(travel.getUser().getImage().getImageOriginTitle())
					.travelLat(travel.getTravelLat())
					.travelLong(travel.getTravelLong())
					.startDate(travel.getStartDate())
					.endDate(travel.getEndDate())
					.imageWidth(travel.getImage().getImageWidth())
					.imageHeight(travel.getImage().getImageHeight())
					.totalLike(goodRepository.countByTravel_TravelIdx(travel.getTravelIdx()))
					.userId(travel.getUser().getUserId())
					.userNickname(travel.getUser().getUserNickname())
					.build();
			if(userId!=null) {
				if(goodRepository.findByUser_UserIdAndTravel_TravelIdx(userId, travel.getTravelIdx()).isPresent()) t.setIsLike(1);
				else t.setIsLike(0);
				
				if(relationRepository.findByRelationFollowingAndRelationFollowed(userRepository.findByUserId(userId).get(),travel.getUser()).isPresent()) t.setIsFollow(1);
				else t.setIsFollow(0);
			}
			feeds.add(t);
		}
		resultMap.put("success", feeds);
		resultMap.put("isLast", travels.isLast());
		return new BaseMessage(HttpStatus.OK,resultMap);
	}

}
