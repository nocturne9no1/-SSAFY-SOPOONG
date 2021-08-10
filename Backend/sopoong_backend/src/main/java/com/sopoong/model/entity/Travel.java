package com.sopoong.model.entity;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import com.sun.istack.NotNull;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class)
public class Travel implements Serializable{

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long travelIdx;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "user_id", referencedColumnName = "user_id")
	private User user;
	
	@NotNull
	private String travelTitle;
	
	@OneToOne
	@JoinColumn(name = "place_idx")
	private Place place;
	
	private String travelContent;
	
	@OneToOne
	@JoinColumn(name = "image_idx")
	private Image image;
	
	private boolean travelIsVisible;
	
	@Column(insertable = false, updatable = true)
	private LocalDateTime updatedAt;
	
	@Column(insertable = false, updatable = false)
	private LocalDateTime createdAt;
	
	@OneToMany
	@JoinColumn(name = "travel_idx")
	private List<Place> places = new ArrayList<>();
	
	@OneToMany(mappedBy = "travel")
	private List<Good> goods = new ArrayList<>();

	@Override
	public String toString() {
		String s = "Travel [travelIdx=" + travelIdx + ", user=" + user.getUserId() + ", travelTitle=" + travelTitle + ", place=" + place.getPlaceIdx()
		+ ", travelContent=" + travelContent + ", image=" + image.getImageIdx() + ", travelIsVisible=" + travelIsVisible
		+ ", updatedAt=" + updatedAt + ", createdAt=" + createdAt + ", places=";
		
		for(int i=0;i<places.size();i++) {
			s+=places.get(i).toString();
		}
		s+=", likes=";
		for(int i=0;i<goods.size();i++) {
			s+=goods.get(i).toString();
		}
		s+="]";
		
		return s;
	}

	
	
	
}
