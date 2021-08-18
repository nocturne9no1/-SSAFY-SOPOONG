package com.sopoong.service;

import java.io.File;
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
import com.sopoong.model.dto.PlaceCategory;
import com.sopoong.model.dto.PlaceDto;
import com.sopoong.model.dto.PlaceList;
import com.sopoong.model.dto.PlaceRate;
import com.sopoong.model.dto.TravelDetail;
import com.sopoong.model.dto.TravelDto;
import com.sopoong.model.dto.TravelList;
import com.sopoong.model.entity.Alarm;
import com.sopoong.model.entity.Image;
import com.sopoong.model.entity.Place;
import com.sopoong.model.entity.Relation;
import com.sopoong.model.entity.Travel;
import com.sopoong.model.entity.User;
import com.sopoong.repository.AlarmRepository;
import com.sopoong.repository.GoodRepository;
import com.sopoong.repository.ImageRepository;
import com.sopoong.repository.PlaceRepository;
import com.sopoong.repository.RelationRepository;
import com.sopoong.repository.TravelRepository;
import com.sopoong.repository.UserRepository;

@Service
public class TravelService {

	@Autowired
	private TravelRepository travelRepository;
	
	@Autowired
	private PlaceRepository placeRepository;

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private ImageRepository imageRepository;
	
	@Autowired
	private AlarmRepository alarmRepository;
	
	@Autowired
	private RelationRepository relationRepository;

	@Autowired
	private GoodRepository goodRepository;

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
		Travel travel = Travel.builder().user(userRepository.findByUserId(travelDto.getUserId()).get())
				.travelTitle(travelDto.getTravelTitle()).travelContent(travelDto.getTravelContent())
				.travelIsVisible(travelDto.isTravelIsVisible()).startDate(travelDto.getStartDate())
				.endDate(travelDto.getEndDate()).image(null).travelLat(0.0).travelLong(0.0)
				.startDate(travelDto.getStartDate()).endDate(travelDto.getEndDate()).build();

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
			if (!bmImage.getHttpStatus().equals(HttpStatus.OK)) {
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
	protected BaseMessage updateTravel(Travel travel, Image travelImage) {
		Map<String, Object> resultMap = new HashMap<>();
		Optional<Travel> updateTravel = travelRepository.findBytravelIdx(travel.getTravelIdx());
		
		System.out.println("[travelIdx] " + travel.getTravelIdx());
		System.out.println("[updateTravel] " + travelImage.getImageIdx() + " " + travelImage.getImageLat());
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

	public BaseMessage selctTravelList(String userId) {
		List<Travel> tlist = travelRepository.findByUser_UserId(userId);
		ArrayList<TravelList> travelList = new ArrayList<>();
		for(TravelList t : travelList) {
			System.out.println(t.toString());
		}
		for (Travel travel : tlist) {
			
			TravelList t = TravelList.builder()
						.travelIdx(travel.getTravelIdx())
						.travelTitle(travel.getTravelTitle())
						.travelContent(travel.getTravelContent())
						.imageOriginTitle(imageRepository.findByImageIdx(travel.getImage().getImageIdx()).get().getImageOriginTitle())
						.travelLat(travel.getTravelLat())
						.travelLong(travel.getTravelLong())
						.startDate(null)
						.endDate(null)
						.totalLike(goodRepository.countByTravel_TravelIdx(travel.getTravelIdx()))
						.build();
			
			System.out.println(t.toString());
			travelList.add(t);
		}
		return new BaseMessage(HttpStatus.OK, travelList);

	}

	public BaseMessage selectTravelDetail(long travelIdx) {
		Optional<Travel> travel = travelRepository.findBytravelIdx(travelIdx);
		
		// travelIdx에 해당하는 Travel 정보 저장
		TravelList t = TravelList.builder()
						.travelIdx(travel.get().getTravelIdx())
						.travelTitle(travel.get().getTravelTitle())
						.travelContent(travel.get().getTravelContent())
						.imageOriginTitle(imageRepository.findByImageIdx(travel.get().getImage().getImageIdx()).get().getImageOriginTitle())
						.travelLat(travel.get().getTravelLat())
						.travelLong(travel.get().getTravelLong())
						.startDate(null)
						.endDate(null)
						.isFollow(travel.get().isFollow())
						.totalLike(goodRepository.countByTravel_TravelIdx(travel.get().getTravelIdx()))
						.build();
		
		// PlaceList[] 정보 저장
		List<Place> plist = placeRepository.findByTravel_TravelIdx(travelIdx); // travelIdx로 검색한 placeList들
		ArrayList<PlaceList> placeList = new ArrayList<>();
		
		for(Place place : plist) {
			if (place == null) {
				return new BaseMessage(HttpStatus.BAD_REQUEST, "error: place is null");
			}
			
			PlaceList p = PlaceList.builder()
						.placeIdx(place.getPlaceIdx())
						.placeTitle(place.getPlaceTitle())
						.placeComment(place.getPlaceComment())
						.placeCategory(new PlaceCategory(place.getPlaceCategory(), place.getPlaceCategory2()))
						.placeRates(new PlaceRate(place.getPlaceRate1(), place.getPlaceRate2(), place.getPlaceRate3()))
						.placeTransport(place.getPlaceTransport())
						.placeVisitDate(place.getPlaceVisitDate())
						.placeLat(place.getPlaceLat())
						.placeLong(place.getPlaceLong())
						.imageOriginTitle(imageRepository.findByImageIdx(place.getImage().getImageIdx()).get().getImageOriginTitle())
						.build();
			
			placeList.add(p);
		}
		
		// TravelDetail에 데이터 저장
		TravelDetail travelDetail = TravelDetail.builder()
								.travel(t)
								.placeList(placeList)
								.build();
		return new BaseMessage(HttpStatus.OK, travelDetail);
	}

	public BaseMessage deleteTravel(long travelIdx) {
		Map<String,Object> resultMap= new HashMap<>();
		Optional<Travel> delTravel= travelRepository.findBytravelIdx(travelIdx);
		
		List<Image> travelImgList = imageRepository.findByTravel_TravelIdx(travelIdx); // 삭제될 이미지
		
		if (delTravel.isPresent()) {
			// 삭제될 이미지들 파일 삭제
			for(Image i : travelImgList) {
				
				String path = i.getImagePath();
				File file = new File(path);
				
				System.out.println("[filePath] " + path);
				if(file.delete()){ // 파일 삭제에 성공하면 true, 실패하면 false
	                System.out.println("파일을 삭제하였습니다");
	            }else{
	                System.out.println("파일 삭제에 실패하였습니다");
	            }
			}
			
			travelRepository.delete(delTravel.get()); 
			
			resultMap.put("success", "Travel 삭제 성공");
			return new BaseMessage(HttpStatus.OK, resultMap);
		} else {
			resultMap.put("errors", "Travel 삭제 실패 (존재하지 않는 travelIdx)");
			return new BaseMessage(HttpStatus.BAD_REQUEST, resultMap);
		}
	}
}
