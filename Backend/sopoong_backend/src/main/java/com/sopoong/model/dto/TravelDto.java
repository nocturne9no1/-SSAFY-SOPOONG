package com.sopoong.model.dto;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.DateTimeFormat.ISO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TravelDto {
	private String userId;
	private String travelTitle;
	private String travelContent;
	private boolean travelIsVisible;
	
	private LocalDateTime startDate;
	
	private LocalDateTime endDate;
	private List<PlaceDto> placeList;
	// 위도 경도 
}
