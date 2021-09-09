package com.quick.utils;

import com.alibaba.fastjson.JSON;

/**
 * @author ：chicunxiang
 * @date ：Created in 2020/9/27 15:13
 * @description：
 * @version: 1.0
 */
public class TokenUtils {

    public static String createToken(Object o){
        String json = JSON.toJSONString(o);
        return MD5.md5(json + "-" + System.currentTimeMillis());
    }
}
