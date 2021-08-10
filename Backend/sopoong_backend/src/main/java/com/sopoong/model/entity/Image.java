package com.sopoong.model.entity;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.sun.istack.NotNull;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "image")
public class Image {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long imageIdx;
	
	@NotNull
	@Column(name = "image_origin_title")
	private String imageOriginTitle; // 업로드 된 실제 파일명
	
	@NotNull
	private String imageTitle; // 서버에 저장된 파일명
	
	@NotNull
	private String imagePath; // 파일 저장 경로
	
	@ManyToOne
	@JoinColumn(name = "travel_idx")
	private Travel travel;
	
	@ManyToOne
	@JoinColumn(name = "place_idx")
	private Place place;
	
	@OneToOne
	@JoinColumn(name = "user_id")
	private User user;
	
	@Column(insertable = false, updatable = false)
	private LocalDateTime createdAt;

	@Override
	public String toString() {
		return "Image [imageIdx=" + imageIdx + ", imageOriginTitle=" + imageOriginTitle + ", imageTitle=" + imageTitle
				+ ", imagePath=" + imagePath + ", travel=" + travel + ", place=" + place + ", user=" + user
				+ ", createdAt=" + createdAt + "]";
	}  
}
