package com.sopoong.model.entity;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.sun.istack.Nullable;
import com.vladmihalcea.hibernate.type.json.JsonStringType;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import springfox.documentation.spring.web.json.Json;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
@TypeDef(name = "jsonb", typeClass = JsonStringType.class)
public class User {

	@Id
	private String userId;	
	
	private String userEmail;
	
	private String userPassword;
	
	private String userNickname;
	
	private String authNumber;
	
	@Type(type = "jsonb")
    @Column(name = "user_preference", columnDefinition = "jsonb", nullable = true)
    private Map<String, String> userPreference = new HashMap<>();
	
	@Column(name="user_is_visible", columnDefinition = "TINYINT", length=1)
	private int userIsVisible;
	
	private int userAlarm;
	
	private String userComment;
	
	@OneToOne
	@JoinColumn(name="image_idx")
	private Image image;
	
	@Column(insertable = false, updatable = false)
	private LocalDateTime createdAt;
	
	@OneToMany
	@JoinColumn(name = "relation_following")
	private List<Relation> relationFollowing = new ArrayList<>();
	
	@OneToMany
	@JoinColumn(name = "relation_followed" )
	private List<Relation> relationFollowed = new ArrayList<>();
	
	@OneToMany
	@JoinColumn(name = "user_id")
	private List<Travel> travels = new ArrayList<>();
	
	@OneToMany
	@Nullable
	@JoinColumn(name = "user_id")
	private List<Good> goods = new ArrayList<>();
	
	@OneToMany
	@Nullable
	@JoinColumn(name = "user_id")
	private List<Scrap> scraps = new ArrayList<>();

	@Override
	public String toString() {
		String s = "";
		s = "User [userId=" + userId + ", userEmail=" + userEmail + ", userPassword=" + userPassword
		+ ", userNickname=" + userNickname + ", authNumber=" + authNumber + ", userPreference=" + userPreference
		+ ", userIsVisible=" + userIsVisible + ", userAlarm=" + userAlarm + ", userComment=" + userComment
		+ ", image=" + image + ", createdAt=" + createdAt + ", relationFollowing=";
		
		for(int i=0;i<relationFollowing.size();i++) {
			s+=relationFollowing.get(i).toString();
		}
		s+= ", relationFollowed=";
		for(int i=0;i<relationFollowed.size();i++) {
			s+=relationFollowed.get(i).toString();
		}
		s += ", travels=" ;
		for(int i=0;i<travels.size();i++) {
			s+=travels.get(i).toString();
		}
		s+=", likes="; 
		for(int i=0;i<goods.size();i++) {
			s+=goods.get(i).toString();
		}
		s+=", scraps=";
		for(int i=0;i<scraps.size();i++) {
			s+=scraps.get(i).toString();
		}
		s+="]";
		
		
		return s;
	}
	
	
}
