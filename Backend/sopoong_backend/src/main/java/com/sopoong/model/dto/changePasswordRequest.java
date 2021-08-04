package com.sopoong.model.dto;

import javax.validation.constraints.NotNull;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
public class changePasswordRequest {
	
	@ApiModelProperty(required = true)
    @NotNull
	String userId;
	
	@ApiModelProperty(required = true)
    @NotNull
	String userEmail;
	
	@ApiModelProperty(required = true)
    @NotNull
	String userPassword;
	
	@ApiModelProperty(required = true)
    @NotNull
    String changedPassword;
	
}
