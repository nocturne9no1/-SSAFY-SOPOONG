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

import org.hibernate.annotations.JoinColumnOrFormula;
import org.hibernate.annotations.JoinColumnsOrFormulas;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Relation {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long relationIdx;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumnOrFormula(column = @JoinColumn(name = "relation_following", insertable = false, updatable = false)			
	)
	private User userFollowing; // 팔로우 한 사람
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumnOrFormula(column = @JoinColumn(name = "relation_followed", insertable = false, updatable = false)			
	)
	private User userFollowed; // 팔로우 당한 사람
	
	@Column(insertable = false, updatable = false)
	private LocalDateTime createdAt;

	@Override
	public String toString() {
		return "Relation [relationIdx=" + relationIdx + ", userFollowing=" + userFollowing.getUserId() + ", userFollowed="
				+ userFollowed.getUserId() + ", createdAt=" + createdAt + "]";
	}
	
	
	
	 
}
