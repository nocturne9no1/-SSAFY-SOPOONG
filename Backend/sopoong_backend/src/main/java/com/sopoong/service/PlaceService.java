package com.sopoong.service;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.sopoong.common.BaseMessage;
import com.sopoong.model.dto.PlaceDto;
import com.sopoong.model.entity.Image;
import com.sopoong.model.entity.Place;
import com.sopoong.repository.ImageRepository;
import com.sopoong.repository.PlaceRepository;
import com.sopoong.repository.TravelRepository;

@Service
public class PlaceService {
	@Autowired
	private PlaceRepository placeRepository;

	@Autowired
	private ImageRepository imageRepository;
	
	@Autowired
	private TravelRepository travelRepository;

	@Transactional
	public BaseMessage writePlace(PlaceDto placeDto, String userId) {
		System.out.println("writePlace 들어옴");
		System.out.println("[placeDto visitDate] " + placeDto.getVisitDate());
		// Map<String,Object> resultMap= new HashMap<>();
		System.out.println(placeDto.getTitle() + " " + placeDto.getComment());
		Place place = Place.builder()
				.placeTitle(placeDto.getTitle())
				.placeCategory(placeDto.getCategory().getMain())
				.placeCategory2(placeDto.getCategory().getSub())
				.placeRate1(placeDto.getRates().getRate1())
				.placeRate2(placeDto.getRates().getRate2())
				.placeRate3(placeDto.getRates().getRate3())
				.placeTransport(placeDto.getTransport())
				.placeComment(placeDto.getComment())
				.image(null)
				.placeLat(0.0)
				.placeLong(0.0)
				.placeVisitDate(placeDto.getVisitDate())
				.build();

		System.out.println("aaa " + place.getPlaceVisitDate());
		place = placeRepository.save(place);
		System.out.println("bbb " +place.getPlaceVisitDate());
		System.out.println("저장 끝");
		// resultMap.put("success", place);
		return new BaseMessage(HttpStatus.OK, place.getPlaceIdx());
	}

	public BaseMessage updatePlace(long placeIdx, long travelIdx, Image placeImage) {
		Map<String, Object> resultMap = new HashMap<>();
		Optional<Place> updatePlace = placeRepository.findByplaceIdx(placeIdx);

		System.out.println("[updatePlace] " + placeImage.getImageLat());
		System.out.println("[updatePlace] " + placeImage.getImageLong());
		System.out.println("[updatePlace] " + placeImage.getImageTime());
		
		if (updatePlace.isPresent()) {
			updatePlace.get().setImage(imageRepository.findByImageIdx(placeImage.getImageIdx()).get());
			updatePlace.get().setPlaceLat(placeImage.getImageLat());
			updatePlace.get().setPlaceLong(placeImage.getImageLong());
			updatePlace.get().setPlaceVisitDate(placeImage.getImageTime());
			updatePlace.get().setTravel(travelRepository.findBytravelIdx(travelIdx).get());
			placeRepository.save(updatePlace.get());

			resultMap.put("success", "Place 대표 사진 등록 완료");
			return new BaseMessage(HttpStatus.OK, resultMap);
		} else {
			resultMap.put("errors", "Place 변경 실패 (존재하지 않는 placeIdx)");
			return new BaseMessage(HttpStatus.BAD_REQUEST, resultMap);
		}
	}
}
