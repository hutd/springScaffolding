package com.quick.vo;

/**
 * @author ：chicunxiang
 * @date ：Created in 2020/9/11 14:43
 * @description：返回结构接口
 * @version: 1.0
 */
public interface IResultEntity<T> {

    void setCode(Integer code);

    void setMsg(String msg);

    void setData(T data);

    void setTimestamp(Long timestamp);

     void setErrTaskMessage(String errTaskMessage);
}
