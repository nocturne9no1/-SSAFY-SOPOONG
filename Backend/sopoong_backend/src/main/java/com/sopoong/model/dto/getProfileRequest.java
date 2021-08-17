package com.sopoong.model.dto;

import com.sopoong.model.entity.Image;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class getProfileRequest {
	
	String userId;
	
	String userNickname;
	
	int userIsVisible;
	
	String userComment;
	
	Image image;
	
	long followings;
	
	long followers;
	
}
