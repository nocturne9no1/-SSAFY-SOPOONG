package com.sopoong.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.sopoong.common.BaseMessage;
import com.sopoong.model.dto.ScrapDto;
import com.sopoong.model.dto.ScrapRequest;
import com.sopoong.model.entity.Scrap;
import com.sopoong.model.entity.Travel;
import com.sopoong.model.entity.User;
import com.sopoong.repository.ScrapRepository;
import com.sopoong.repository.TravelRepository;
import com.sopoong.repository.UserRepository;

@Service
public class ScrapService {
	@Autowired
	private ScrapRepository scrapRepository;
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private TravelRepository travelRepository;
	
	public BaseMessage getScraps(String id, Pageable pageable) {
		Map<String,Object> resultMap = new HashMap<>();
		Page<Scrap> scraps;
		Optional<User> userOpt = userRepository.findByUserId(id);
		if(userOpt.isPresent()) {
			scraps = scrapRepository.findByUser_UserId(id, pageable);
			List<ScrapDto> scrapDtos = new ArrayList<>();
			for(int i=0;i<scraps.getContent().size();i++) {
				Scrap scrap = scraps.getContent().get(i);
				scrapDtos.add(ScrapDto.builder().travelIdx(scrap.getTravel().getTravelIdx())
						.userId(scrap.getUser().getUserId()).userNickname(scrap.getUser().getUserNickname())
						.profileImagePath(scrap.getUser().getImage().getImagePath())
						.travelTitle(scrap.getTravel().getTravelTitle()).travelComment(scrap.getTravel().getTravelContent())
						.travelImagePath(scrap.getTravel().getImage().getImageTitle())
						.travelIsVisible(scrap.getTravel().getTravelIsVisible()).build());
			}
			resultMap.put("success", scrapDtos);
			resultMap.put("isLast", scraps.isLast());
		}else {
			resultMap.put("error", "존재하지 않는 아이디");
			return new BaseMessage(HttpStatus.BAD_REQUEST,resultMap);
		}
		return new BaseMessage(HttpStatus.OK,resultMap);
	}
	
	public BaseMessage scrap(ScrapRequest scrapRequest) {
		Map<String,Object> resultMap = new HashMap<>();
		Optional<User> userOpt = userRepository.findByUserId(scrapRequest.getUserId());
		if(!userOpt.isPresent()) {
			resultMap.put("errors", "존재하지 않는 id");
			return new BaseMessage(HttpStatus.BAD_REQUEST,resultMap);
		}
		Optional<Travel> travelOpt = travelRepository.findById(scrapRequest.getTravelIdx());
		if(!travelOpt.isPresent()) {
			resultMap.put("errors", "존재하지 않는 여행게시글");
			return new BaseMessage(HttpStatus.BAD_REQUEST,resultMap);
		}
		Optional<Scrap> scrapOpt = scrapRepository.findByUser_UserIdAndTravel_TravelIdx(scrapRequest.getUserId(),scrapRequest.getTravelIdx());
		if(!scrapOpt.isPresent()) {
			scrapRepository.save(Scrap.builder().user(userOpt.get()).travel(travelOpt.get()).build());
			resultMap.put("success", "스크랩 성공");
		}else {
			scrapRepository.delete(scrapOpt.get());
			resultMap.put("success", "스크랩 취소");
		}
		return new BaseMessage(HttpStatus.OK,resultMap);
	}
}
