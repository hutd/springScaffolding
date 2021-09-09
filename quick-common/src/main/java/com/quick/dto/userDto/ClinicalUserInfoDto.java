package com.quick.dto.userDto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotEmpty;

@Data
public class ClinicalUserInfoDto {

    @NotEmpty(message="医生id不为null且不为空")
    @ApiModelProperty(value = "医生id")
    public String doctor;

    @NotEmpty(message="患者id不为null且不为空")
    @ApiModelProperty(value = "患者id")
    public String patient;

    @NotEmpty(message="病房id不为null且不为空")
    @ApiModelProperty(value = "病房id")
    public String ward;

    @NotEmpty(message="部门id不为null且不为空")
    @ApiModelProperty(value = "部门id")
    public String dept;

    @NotEmpty(message="类型id不为null且不为空")
    @ApiModelProperty(value = "类型id")
    public String type;

}
