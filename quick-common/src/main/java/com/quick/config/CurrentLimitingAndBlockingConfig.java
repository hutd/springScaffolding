package com.quick.config;

import com.alibaba.csp.sentinel.slots.block.BlockException;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class CurrentLimitingAndBlockingConfig {

    // 处理限流与阻塞（服务降级返回友好提示）
    public static String blockHandlerFunc(String a, BlockException e){
        log.warn("限流了==="+a,e);
        return "当前访问人数过多,请稍后再试";
    }


    // 服务异常（降级处理）
    public static String fallbackFunc(String a){
        log.warn("异常==="+a);
        return "";
    }
}