package com.sopoong.model.entity;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Alarm {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long alarmIdx;
	
	@OneToOne
	@JoinColumn(name = "user_id", referencedColumnName = "user_id")
	private User user;
	
	private int alarmCategory;
	
	private int alarmCheck;
	
	@OneToOne
	@JoinColumn(name = "goodIdx")
	private Good good;
	
	@OneToOne
	@JoinColumn(name = "relationIdx")
	private Relation relation;
	
	@OneToOne
	@JoinColumn(name = "travelIdx")
	private Travel travel;
	
	private LocalDateTime createdAt;

	@Override
	public String toString() {
		return "Alarm [alarmIdx=" + alarmIdx + ", user=" + user.getUserId() + ", alarmCategory=" + alarmCategory + ", alarmCheck="
				+ alarmCheck + ", goodIdx=" + good.getGoodIdx() + ", relationIdx=" + relation.getRelationIdx() + ", travelIdx=" + travel.getTravelIdx() + ", createdAt=" + createdAt + "]";
	}
	
	
}
