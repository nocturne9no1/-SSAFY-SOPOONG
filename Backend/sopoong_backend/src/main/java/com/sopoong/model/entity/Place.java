package com.sopoong.model.entity;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
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
public class Place {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long placeIdx;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "travel_idx")
	private Travel travel;
	
	@NotNull
	private String placeTitle;
	
	private String placeCategory;
	
	private String placeCategory2;
	
	private int placeRate1;
	
	private int placeRate2;
	
	private int placeRate3;
	
	private String placeTransport;
	
	private String placeComment;
	
	@OneToOne
	@JoinColumn(name = "image_idx")
	private Image image;
	
	private LocalDateTime placeVisitDate;
	
	private double placeLat;
	
	private double placeLong;
	
	@Column(insertable = false, updatable = false)
	private LocalDateTime createdAt;

	@Override
	public String toString() {
		return "Place [placeIdx=" + placeIdx + ", travel=" + travel.getTravelIdx() + ", placeTitle=" + placeTitle + ", placeCategory="
				+ placeCategory + ", placeCategory2=" + placeCategory2 + ", placeRate1=" + placeRate1 + ", placeRate2="
				+ placeRate2 + ", placeRate3=" + placeRate3 + ", placeTransport=" + placeTransport + ", placeComment="
				+ placeComment + ", image=" + image.getImageIdx() + ", placeVisitDate=" + placeVisitDate + ", placeLat=" + placeLat
				+ ", placeLong=" + placeLong + ", createdAt=" + createdAt + "]";
	}
	
	
}
