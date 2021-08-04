package com.sopoong.model.dto;

import javax.persistence.Column;
import javax.validation.constraints.NotNull;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class changeVisibleRequest {
	
	@ApiModelProperty(required = true)
    @NotNull
	String userId;
	
	@ApiModelProperty(required = true)
    @NotNull
	@Column(name="user_is_visible", columnDefinition = "TINYINT", length=1)
	int userIsVisible;
	
}
