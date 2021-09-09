package com.quick.vo;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @author ：chicunxiang
 * @date ：Created in 2020/9/14 10:33
 * @description：
 * @version: 1.0
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ResponseEntity<T> implements Serializable {
    @JSONField(alternateNames = {"errorCode", "Code", "code", "resultCode"})
    private String code;
    @JSONField(alternateNames = {"Data", "count", "data", "res","results","fpqqlsh"})
    private T data;
    @JSONField(name = "GuidRequest")
    private String guidRequest;
    @JSONField(alternateNames = {"errorMsg", "Message", "message", "msg"})
    private String msg;
    @JSONField(alternateNames = {"timestamp"})
    private Long timestamp;
    @JSONField(alternateNames = {"list"})
    private String[] list;
    @JSONField(alternateNames = {"status"})
    private String status;
}
