package com.quick.utils;

import org.apache.commons.lang3.StringUtils;

import java.util.UUID;

/**
 * @author ：chicunxiang
 * @date ：Created in 2020/9/24 16:21
 * @description：
 * @version: 1.0
 */
public class Utils {

    public static String getUUID() {
        return StringUtils.replace(UUID.randomUUID().toString(), "-", "");
    }

    public final static  String[] RESULT_CODES = {"0","200", "000000","0000","success"};

    public static boolean checkSuccessCode(String code) {
        if (StringUtils.isBlank(code)) {
            return false;
        }
        for (String resultCode : RESULT_CODES) {
            if (resultCode.equals(code)) {
                return true;
            }
        }
        return false;
    }

}
