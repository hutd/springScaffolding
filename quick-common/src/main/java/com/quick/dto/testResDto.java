package com.quick.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
/*接受返回的对象时使用*/
@ApiModel
public class testResDto {

    /*接收出入参时使用*/
    @ApiModelProperty(value = "接收出入参时使用")
    String id;

}
