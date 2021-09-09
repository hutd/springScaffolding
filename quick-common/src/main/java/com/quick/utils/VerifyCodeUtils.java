package com.quick.utils;

import java.util.Random;

/**
 * @author ：chicunxiang
 * @date ：Created in 2020/9/15 15:29
 * @description：
 * @version: 1.0
 */
public class VerifyCodeUtils {


    public static final String VERIFY_CODES = "0123456789";


    public static String generateVerifyCode(int verifySize) {
        Random rand = new Random();
        StringBuilder verifyCode = new StringBuilder(verifySize);
        for (int i = 0; i < verifySize; i++) {
            verifyCode.append(VERIFY_CODES.charAt(rand.nextInt(9)));
        }
        return verifyCode.toString();
    }

    public static void main(String[] args) {
        System.out.println(generateVerifyCode(3));
        System.out.println(generateVerifyCode(4));
        System.out.println(generateVerifyCode(5));
        System.out.println(generateVerifyCode(6));
    }
}
