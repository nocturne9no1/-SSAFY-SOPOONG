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

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import com.sun.istack.NotNull;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Image {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long imageIdx;
	
	@NotNull
	private String imageTitle;
	
	@NotNull
	private String imagePath;
	
	@ManyToOne
	@JoinColumn(name = "travel_idx")
	private Travel travel;
	
	@ManyToOne
	@JoinColumn(name = "place_idx")
	private Place place;
	
	@OneToOne
	@JoinColumn(name = "user_id", referencedColumnName = "user_id")
	private User user;
	
	@Column(insertable = false, updatable = false)
	private LocalDateTime createdAt;

	@Override
	public String toString() {
		return "Image [imageIdx=" + imageIdx + ", imageTitle=" + imageTitle + ", imagePath=" + imagePath + ", travel="
				+ travel + ", place=" + place + ", user=" + user.getUserId() + ", createdAt=" + createdAt + "]";
	}
	
	
}
