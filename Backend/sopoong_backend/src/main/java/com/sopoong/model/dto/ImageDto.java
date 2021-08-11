package com.sopoong.model.dto;

import java.time.LocalDateTime;

import com.sopoong.model.entity.Image;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ImageDto {
	private long imageIdx; // 이미지 번호
	private String imageOriginTitle; // 이미지 실제 이름
	private String imageTitle; // 디비 이미지 이름
	private String imagePath; // 이미지 경로
	private long travelIdx; // 여행일지 번호
	private long placeIdx; // 위치일지 번호
	private String userId; // 유저 아이디
	private LocalDateTime createdAt; // 업로드 시간
	
    public Image toEntity() {
    	Image build = Image.builder()
                .imageOriginTitle(imageOriginTitle)
                .imageTitle(imageTitle)
                .imagePath(imagePath)
                .build();
        return build;
    }
    
    @Builder
    public ImageDto(Long imageIdx, String imageOriginTitle, String imageTitle, String imagePath) {
        this.imageIdx = imageIdx;
        this.imageOriginTitle = imageOriginTitle;
        this.imageTitle = imageTitle;
        this.imagePath = imagePath;
    }
}
