package com.sopoong.model.dto;

import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.validation.constraints.NotNull;

import com.sopoong.model.entity.Image;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class changeProfileRequest {
	
	@ApiModelProperty(required = true)
    @NotNull
	String userId;
	
	@ApiModelProperty(required = true)
	@NotNull
	String userNickname;
	
	@ApiModelProperty(required = false)
	String userComment;
	
	@OneToOne
	@JoinColumn(name="image_idx")
	Image image;
	
}
