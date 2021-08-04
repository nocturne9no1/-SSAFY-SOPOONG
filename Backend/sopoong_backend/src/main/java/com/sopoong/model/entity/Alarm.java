package com.sopoong.model.entity;

import java.time.LocalDateTime;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Alarm {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long alarmIdx;
	
	@OneToOne
	@JoinColumn(name = "user_id")
	private User user;
	
	private int alarmCategory;
	
	private boolean alarmCheck;
	
	private long articleIdx;
	
	private LocalDateTime createdAt;

	@Override
	public String toString() {
		return "Alarm [alarmIdx=" + alarmIdx + ", user=" + user.getUserId() + ", alarmCategory=" + alarmCategory + ", alarmCheck="
				+ alarmCheck + ", articleIdx=" + articleIdx + ", createdAt=" + createdAt + "]";
	}
	
	
}
