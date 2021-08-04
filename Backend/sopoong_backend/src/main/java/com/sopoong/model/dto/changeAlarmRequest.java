package com.sopoong.model.dto;

import javax.validation.constraints.NotNull;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class changeAlarmRequest {
	
	@ApiModelProperty(required = true)
    @NotNull
	String userId;
	
	@ApiModelProperty(required = true)
    @NotNull
	int userAlarm;
	
}
