package com.sopoong.model.dto;

import java.time.LocalDateTime;

import org.springframework.format.annotation.DateTimeFormat;

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
	private boolean isFollow;
	private boolean travelIsVisible;
	private long totalLike;
	private int imageWidth;
	private int imageHeight;
}
