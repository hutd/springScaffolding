package com.quick.vo;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.io.Serializable;

/**
 * @author ：chicunxiang
 * @date ：Created in 2020/9/11 14:28
 * @description：基本返回结构
 * @version: 1.0
 */
@JsonIgnoreProperties({"hibernateLazyInitializer","handler"})
public class BasicResult<T> implements Serializable, IResultEntity<T> {

    private Integer code;

    private String msg;

    private T data;

    private String  errTaskMessage;

    private Long timestamp = System.currentTimeMillis();

    public Integer getCode() {
        return code;
    }

    @Override
    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    @Override
    public void setMsg(String msg) {
        this.msg = msg;
    }

    public Long getTimestamp() {
        return timestamp;
    }

    @Override
    public void setTimestamp(Long timestamp) {
        this.timestamp = timestamp;
    }

    public T getData() {
        return data;
    }

    @Override
    public void setData(T data) {
        this.data = data;
    }

    public String getErrTaskMessage() {
        return errTaskMessage;
    }

    @Override
    public void setErrTaskMessage(String errTaskMessage) {
        this.errTaskMessage = errTaskMessage;
    }

    @Override
    public String toString() {
        return "请求结果为{" +
                "code=" + getCode() +
                ", msg='" + getMsg() + '\'' +
                ", timestamp=" + getTimestamp() + ",data=" + getData()+
                "}";
    }
}
