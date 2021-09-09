package com.quick.vo;

import org.springframework.http.HttpStatus;

/**
 * @author ：chicunxiang
 * @date ：Created in 2020/9/11 14:30
 * @description：
 * @version: 1.0
 */
public class ResultEntity<T> {

    /**
     * {
     * "code": 200,
     * "msg": "OK",
     * "data": object
     * }
     *
     * @param t   数据集合
     * @param <T> 泛型
     * @return 返回数据结构
     */
    public static <T> IResultEntity<T> ok(T t) {
        IResultEntity<T> result = new BasicResult<>();
        result.setData(t);
        result.setCode(HttpStatus.OK.value());
        result.setMsg(HttpStatus.OK.getReasonPhrase());
        return result;
    }


    /**
     * {
     * "code": 200,
     * "msg": "OK",
     * }
     *
     * @return 返回数据结构
     */
    public static IResultEntity<Void> ok() {
        return noContent();
    }


    /**
     * {
     * "code": 200,
     * "msg": "OK"
     * }
     *
     * @return 没有data的返回
     */
    public static IResultEntity<Void> noContent() {
        return status(HttpStatus.OK);
    }

    /**
     *
     * @return 直接返回整个body
     */
    public static <T> IResultEntity<T> body(ResponseEntity<T> responseEntity) {
        IResultEntity<T> result = new BasicResult<>();
        result.setCode(Integer.parseInt(responseEntity.getCode()));
        result.setMsg(responseEntity.getMsg());
        result.setData(responseEntity.getData());
        result.setTimestamp(responseEntity.getTimestamp());
        return result;
    }

    /**
     * {
     * "code": 505,
     * "msg": "HTTP Version not supported"
     * }
     *
     * @return 从httpStatus中取的返回
     */
    public static IResultEntity<Void> status(HttpStatus httpStatus) {
        IResultEntity<Void> result = new BasicResult<>();
        result.setCode(httpStatus.value());
        result.setMsg(httpStatus.getReasonPhrase());
        return result;
    }


    /**
     * {
     * "code": 100201,
     * "msg": "自定义错误"
     * }
     *
     * @return 自定义返回的错误码和错误信息
     */
    public static IResultEntity<Void> fail(int code, String msg) {
        IResultEntity<Void> result = new BasicResult<>();
        result.setCode(code);
        result.setMsg(msg);
        return result;
    }

    /**
     * 统一错误处理
     *
     * @param exceptionResult 自定义错误
     * @return 返回错误信息
     */
    public static IResultEntity<Void> fail(ExceptionResult exceptionResult) {
        IResultEntity<Void> result = new BasicResult<>();
        result.setCode(exceptionResult.getCode());
        result.setMsg(exceptionResult.getMsg());
        result.setTimestamp(exceptionResult.getTimestamp());
        return result;
    }

    /**
     * 统一错误处理
     *
     * @param exceptionResult 自定义错误
     * @return 返回带错误信息的错误信息
     */
    public static IResultEntity<Void> fail(ExceptionResult exceptionResult, Exception e) {
        IResultEntity<Void> result = new BasicResult<>();
        result.setCode(exceptionResult.getCode());
        result.setErrTaskMessage(e == null ? null : e.getMessage());
        result.setMsg(exceptionResult.getMsg());
        result.setTimestamp(exceptionResult.getTimestamp());
        return result;
    }
}
