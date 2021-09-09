package com.quick.vo;

import com.quick.enums.ExceptionEnum;
import lombok.Data;

/**
 * @author ：彭来祥
 * @date ：Created in 2020/8/24 22:05
 * @description：
 * @version:
 */
@Data
public class ExceptionResult {

    private int code;

    private String msg;

    private long timestamp;
    public  ExceptionResult(ExceptionEnum em) {
        this.code = em.value();
        this.msg = em.message();
        this.timestamp = System.currentTimeMillis();
    }
}
