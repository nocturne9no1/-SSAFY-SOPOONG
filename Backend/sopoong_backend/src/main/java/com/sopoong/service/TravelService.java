package com.sopoong.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.sopoong.common.BaseMessage;
import com.sopoong.model.dto.FileDto;
import com.sopoong.model.dto.FollowListRequest;
import com.sopoong.model.dto.PlaceDto;
import com.sopoong.model.dto.TravelDto;
import com.sopoong.model.entity.Alarm;
import com.sopoong.model.entity.Image;
import com.sopoong.model.entity.Relation;
import com.sopoong.model.entity.Travel;
import com.sopoong.repository.AlarmRepository;
import com.sopoong.repository.ImageRepository;
import com.sopoong.repository.RelationRepository;
import com.sopoong.repository.TravelRepository;
import com.sopoong.repository.UserRepository;

@Service
public class TravelService {

	@Autowired
	private TravelRepository travelRepository;

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private ImageRepository imageRepository;
	
	@Autowired
	private AlarmRepository alarmRepository;
	
	@Autowired
	private RelationRepository relationRepository;

	@Autowired
	private PlaceService placeService;
	
	@Autowired
	private ImageService imageService;

	@Transactional
	public BaseMessage writeTravel(TravelDto travelDto) throws Exception {
		Map<String, Object> resultMap = new HashMap<>();

		String userId = travelDto.getUserId();
		long travelIdx;
		long placeIdx;
		System.out.println("[userId] " + travelDto.getUserId());
		System.out.println(travelDto.getTravelTitle() + " " + travelDto.getTravelContent());
		
		/* 1. 여행 기록 */
		Travel travel = Travel.builder()
				.user(userRepository.findByUserId(travelDto.getUserId()).get())
				.travelTitle(travelDto.getTravelTitle())
				.travelContent(travelDto.getTravelContent())
				.travelIsVisible(travelDto.isTravelIsVisible())
				.startDate(travelDto.getStartDate())
				.endDate(travelDto.getEndDate())
				.image(null)
				.travelLat(0.0)
				.travelLong(0.0)
				.startDate(travelDto.getStartDate())
				.endDate(travelDto.getEndDate()).build();

		travel = travelRepository.save(travel);
		resultMap.put("success", travel);

		// 여행 기록 idx 가져오기
		travelIdx = travel.getTravelIdx();
		System.out.println("[travelIdx] " + travelIdx);

		/* 2. 위치 기록 */
		List<PlaceDto> places = travelDto.getPlaceList();
		for (PlaceDto place : places) {
			System.out.println("[DateTime] " + place.getVisitDate());
			// 위치기록 저장
			BaseMessage bmPlace = placeService.writePlace(place, userId);
			if (!bmPlace.getHttpStatus().equals(HttpStatus.OK))
				return new BaseMessage(HttpStatus.BAD_REQUEST, bmPlace.getData());
			System.out.println("위치 저장 끝");

			// 위치 기록 idx를 사진 테이블에 저장
			placeIdx = (long) bmPlace.getData();
			System.out.println("[placeIdx] " + placeIdx);

			/* 3. 사진 기록 */
			List<FileDto> files = place.getImageList();
			BaseMessage bmImage = imageService.saveImage(files, userId, travelIdx, placeIdx);
			if(!bmImage.getHttpStatus().equals(HttpStatus.OK)) {
				return new BaseMessage(HttpStatus.BAD_REQUEST, bmImage.getData());
			}

			/* place 대표사진 idx 구하기 */
			Image placeImage = imageRepository.findImageByPlace_PlaceIdxAndIsPlaceLeader(placeIdx, 1).get();

			// place 테이블에 저장
			bmPlace = placeService.updatePlace(placeIdx, travelIdx, placeImage);
			System.out.println("place 대표 이미지 저장 완료");
		}

		/* travel 대표사진 idx 구하기 */
		Image travelImage = imageRepository.findImageByTravel_TravelIdxAndIsTravelLeader(travelIdx, 1).get();
	
		// travel 테이블에 저장
		updateTravel(travel, travelImage);
		
		// 알람
		long index= travel.getTravelIdx();		
		List<Relation> followedList= userRepository.findByUserId(travelDto.getUserId()).get().getRelationFollowed();
		
		for (int i=0; i<followedList.size(); i++) {
			if (String.format("%03d", Integer.parseInt(Integer.toBinaryString(userRepository.findByUserId(followedList.get(i).getRelationFollowed().getUserId()).get().getUserAlarm()))).charAt(0)=='1') {
				alarmRepository.save(Alarm.builder()
						.user(followedList.get(i).getRelationFollowing())
						.alarmCategory(3)
						.alarmCheck(0)
						.travel(travelRepository.findById(index).get())
						.build());
			}
		}

		System.out.println("끄읕");
		return new BaseMessage(HttpStatus.OK, "성공");
	}

	@Transactional
	private BaseMessage updateTravel(Travel travel, Image travelImage) {
		Map<String, Object> resultMap = new HashMap<>();
		Optional<Travel> updateTravel = travelRepository.findBytravelIdx(travel.getTravelIdx());

		if (updateTravel.isPresent()) {
			updateTravel.get().setImage(imageRepository.findByImageIdx(travelImage.getImageIdx()).get());
			updateTravel.get().setTravelLat(travelImage.getImageLat());
			updateTravel.get().setTravelLong(travelImage.getImageLong());

			travelRepository.save(updateTravel.get());
			resultMap.put("success", "Place 대표 사진 등록 완료");
			return new BaseMessage(HttpStatus.OK, resultMap);
		} else {
			resultMap.put("errors", "Place 변경 실패 (존재하지 않는 placeIdx)");
			return new BaseMessage(HttpStatus.BAD_REQUEST, resultMap);
		}
	}
}
