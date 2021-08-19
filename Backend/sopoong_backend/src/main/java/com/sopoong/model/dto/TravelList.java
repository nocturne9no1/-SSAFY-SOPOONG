package com.sopoong.model.dto;

import java.time.LocalDateTime;

import org.springframework.format.annotation.DateTimeFormat;

import com.sopoong.model.entity.Travel;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TravelList {
	private long travelIdx;
	private String travelTitle;
	private String travelContent;
	private String imageOriginTitle;
	private double travelLat;
	private double travelLong;
	private LocalDateTime startDate;
	private LocalDateTime endDate;
	private int isFollow;
	private int travelIsVisible;
	private long totalLike;
	private int imageWidth;
	private int imageHeight;
	private String userId;
	private String userNickname;
	TravelList(Travel travel){
		travelIdx = travel.getTravelIdx();
		travelTitle = travel.getTravelTitle();
		travelContent = travel.getTravelContent();
		imageOriginTitle = travel.getImage().getImageOriginTitle();
		travelLat = travel.getTravelLat();
		travelLong = travel.getTravelLong();
		startDate = travel.getStartDate();
		endDate = travel.getEndDate();
		isFollow = travel.getIsFollow();
		travelIsVisible = travel.getTravelIsVisible();
	}
}
