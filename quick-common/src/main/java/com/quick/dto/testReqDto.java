package com.quick.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;

@Data
public class testReqDto {

    /*接收出入参时使用*/
    @ApiModelProperty(value = "接收出入参时使用")
    String id;

    /*验证注解的元素值不为null且不为空（字符串长度不为0、集合大小不为0）*/
    @NotEmpty(message="参数不为null且不为空")
    String name;

    /*验证注解的元素值不为空*/
    @NotBlank(message="参数的值不为空")
    String age;

    /*限制必须不为null*/
    @NotNull(message="参数不为null")
    String sex;

    /*如果有list，可用@Valid校验list里的每个值*/
    @Valid
    @NotEmpty(message="参数不为null且不为空")
    public List<String> aaa;
}
