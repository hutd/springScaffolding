package com.quick.enums;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
public enum ExceptionEnum {

    INVALID_PARAM(400, "参数错误"),
    USER_TOKEN_FAILED(4521, "用户token过期,请重新获取"),
    UPLOAD_FAILED(10086, "上传失败"),
    DEL_FAILED(10086, "删除失败"),
    DOWN_FAILED(10086, "下载失败"),
    DEL_SUCCESS(10086, "删除成功"),
    SYSTEM_UNKNOWN_FAILED(10028, "出现系统未知错误")
    ;
    int value;
    String message;


    public int value() {
        return this.value;
    }

    public String message() {
        return this.message;
    }
}
