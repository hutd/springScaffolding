package com.quick.utils;

import com.alibaba.fastjson.JSON;
import org.apache.commons.codec.binary.Base64;

import javax.crypto.Cipher;
import java.security.*;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.HashMap;
import java.util.Map;

/**
 * RSA安全编码组件 合肥加密
 *
 * @author fishlord
 * 2016年6月5日上午1:39:20
 */
public abstract class CertifyRSAUtils {
    public static final String KEY_ALGORITHM = "RSA";
    public static final String SIGNATURE_ALGORITHM = "SHA256WithRSA";

    private static final String PUBLIC_KEY = "RSAPublicKey";
    private static final String PRIVATE_KEY = "RSAPrivateKey";

    /**
     * 用私钥对信息生成数字签名
     *
     * @param data       加密数据
     * @param privateKey 私钥
     * @return
     */
    public static String sign(byte[] data, String privateKey) {
        // 解密由base64编码的私钥
        // KEY_ALGORITHM 指定的加密算法
        try {
            byte[] keyBytes = Base64.decodeBase64(privateKey);

            // 构造PKCS8EncodedKeySpec对象
            PKCS8EncodedKeySpec pkcs8KeySpec = new PKCS8EncodedKeySpec(keyBytes);
            KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);

            // 取私钥匙对象
            PrivateKey priKey = keyFactory.generatePrivate(pkcs8KeySpec);

            // 用私钥对信息生成数字签名
            Signature signature = Signature.getInstance(SIGNATURE_ALGORITHM);
            signature.initSign(priKey);
            signature.update(data);

            return Base64.encodeBase64String(signature.sign());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }

    /**
     * 校验数字签名
     *
     * @param data      加密数据
     * @param publicKey 公钥
     * @param sign      数字签名
     * @return 校验成功返回true 失败返回false
     * @throws Exception
     */
    public static boolean verify(byte[] data, String publicKey, String sign)
            throws Exception {

        // 解密由base64编码的公钥
        byte[] keyBytes = Base64.decodeBase64(publicKey);

        // 构造X509EncodedKeySpec对象
        X509EncodedKeySpec keySpec = new X509EncodedKeySpec(keyBytes);

        // KEY_ALGORITHM 指定的加密算法
        KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);

        // 取公钥匙对象
        PublicKey pubKey = keyFactory.generatePublic(keySpec);

        Signature signature = Signature.getInstance(SIGNATURE_ALGORITHM);
        signature.initVerify(pubKey);
        signature.update(data);

        // 验证签名是否正常
        return signature.verify(Base64.decodeBase64(sign));
    }

    /**
     * 解密<br>
     * 用私钥解密
     *
     * @param data
     * @param key
     * @return
     * @throws Exception
     */
    public static String decryptByPrivateKey(byte[] data, String key)
            throws Exception {
        // 对密钥解密
        byte[] keyBytes = Base64.decodeBase64(key);

        // 取得私钥
        PKCS8EncodedKeySpec pkcs8KeySpec = new PKCS8EncodedKeySpec(keyBytes);
        KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);
        Key privateKey = keyFactory.generatePrivate(pkcs8KeySpec);

        // 对数据解密
        Cipher cipher = Cipher.getInstance(keyFactory.getAlgorithm());
        cipher.init(Cipher.DECRYPT_MODE, privateKey);

        return new String(cipher.doFinal(Base64.decodeBase64(data)));
    }

    /**
     * 解密<br>
     * 用公钥解密
     *
     * @param data
     * @param key
     * @return
     * @throws Exception
     */
    public static byte[] decryptByPublicKey(byte[] data, String key)
            throws Exception {
        // 对密钥解密
        byte[] keyBytes = Base64.decodeBase64(key);

        // 取得公钥
        X509EncodedKeySpec x509KeySpec = new X509EncodedKeySpec(keyBytes);
        KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);
        Key publicKey = keyFactory.generatePublic(x509KeySpec);

        // 对数据解密
        Cipher cipher = Cipher.getInstance(keyFactory.getAlgorithm());
        cipher.init(Cipher.DECRYPT_MODE, publicKey);

        return cipher.doFinal(Base64.decodeBase64(data));
    }

    /**
     * 加密<br>
     * 用公钥加密
     *
     * @param data
     * @param key
     * @return
     * @throws Exception
     */
    public static String encryptByPublicKey(byte[] data, String key)
            throws Exception {
        // 对公钥解密
        byte[] keyBytes = Base64.decodeBase64(key);

        // 取得公钥
        X509EncodedKeySpec x509KeySpec = new X509EncodedKeySpec(keyBytes);
        KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);
        Key publicKey = keyFactory.generatePublic(x509KeySpec);

        // 对数据加密
        Cipher cipher = Cipher.getInstance(keyFactory.getAlgorithm());
        cipher.init(Cipher.ENCRYPT_MODE, publicKey);

        return Base64.encodeBase64String(cipher.doFinal(data));
    }

    /**
     * 加密<br>
     * 用私钥加密
     *
     * @param data
     * @param key
     * @return
     * @throws Exception
     */
    public static byte[] encryptByPrivateKey(byte[] data, String key)
            throws Exception {
        // 对密钥解密
        byte[] keyBytes = Base64.decodeBase64(key);

        // 取得私钥
        PKCS8EncodedKeySpec pkcs8KeySpec = new PKCS8EncodedKeySpec(keyBytes);
        KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);
        Key privateKey = keyFactory.generatePrivate(pkcs8KeySpec);

        // 对数据加密
        Cipher cipher = Cipher.getInstance(keyFactory.getAlgorithm());
        cipher.init(Cipher.ENCRYPT_MODE, privateKey);

        return cipher.doFinal(data);
    }

    /**
     * 取得私钥
     *
     * @param keyMap
     * @return
     * @throws Exception
     */
    public static String getPrivateKey(Map<String, Object> keyMap)
            throws Exception {
        Key key = (Key) keyMap.get(PRIVATE_KEY);

        return Base64.encodeBase64String(key.getEncoded());
    }

    /**
     * 取得公钥
     *
     * @param keyMap
     * @return
     * @throws Exception
     */
    public static String getPublicKey(Map<String, Object> keyMap)
            throws Exception {
        Key key = (Key) keyMap.get(PUBLIC_KEY);

        return Base64.encodeBase64String(key.getEncoded());
    }

    /**
     * 初始化密钥
     *
     * @return
     * @throws Exception
     */
    public static Map<String, Object> initKey() throws Exception {
        KeyPairGenerator keyPairGen = KeyPairGenerator
                .getInstance(KEY_ALGORITHM);
        keyPairGen.initialize(2048);

        KeyPair keyPair = keyPairGen.generateKeyPair();

        // 公钥
        RSAPublicKey publicKey = (RSAPublicKey) keyPair.getPublic();

        // 私钥
        RSAPrivateKey privateKey = (RSAPrivateKey) keyPair.getPrivate();

        Map<String, Object> keyMap = new HashMap<String, Object>(2);

        keyMap.put(PUBLIC_KEY, publicKey);
        keyMap.put(PRIVATE_KEY, privateKey);
        return keyMap;
    }


    public static void main(String[] args) throws Exception {



        String pubKey = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAlBWpXeteta/JOAxsNuEICiLbJH989UjL00nFN1VtOZwKQwFmQbMunW3pwKyF3tCp2oOQtDG9+PwpWyIBvJfPSnOhugX9d211dzQcGJLLiJ8j9A7BUTu9wO39NpPyp1r3P0izhi8ZYeKR2wUiwq0Nrb3HsEQMEuV/HDpOHlLBrHP9F3ldhO73+JIifTN4ucdhZUMbRI3Rp5wWr54YcQ03ttazGEHbl6OGv5w2F66uZFD/iTJ3kdc0N1XbUP0nIU8nr3Rhbbx9jIql/KsdNZdsGooXm75HbAmDAfBC0dWWmSV8kNsay5QsmE7Ns6NL6FhQD++DW5LtPkGEjrbE0mEoLQIDAQAB";
        String priKey = "MIIEvwIBADANBgkqhkiG9w0BAQEFAASCBKkwggSlAgEAAoIBAQCUFald6161r8k4DGw24QgKItskf3z1SMvTScU3VW05nApDAWZBsy6dbenArIXe0Knag5C0Mb34/ClbIgG8l89Kc6G6Bf13bXV3NBwYksuInyP0DsFRO73A7f02k/KnWvc/SLOGLxlh4pHbBSLCrQ2tvcewRAwS5X8cOk4eUsGsc/0XeV2E7vf4kiJ9M3i5x2FlQxtEjdGnnBavnhhxDTe21rMYQduXo4a/nDYXrq5kUP+JMneR1zQ3VdtQ/SchTyevdGFtvH2MiqX8qx01l2waihebvkdsCYMB8ELR1ZaZJXyQ2xrLlCyYTs2zo0voWFAP74Nbku0+QYSOtsTSYSgtAgMBAAECggEAGrsGRZPVFsrScbGcXxOj1eCQ2jfBIK2xTUSFuwVKoBmQqKO6B6YGhhhvF36M87mKz4zsM/q+phEujerHkXl9y5yv1tp59HLnv0rCv5TP9Kt1W/rF3poAGLFdAdsW9CqxctT2wwQBeu/hED4PFkEStQgK4cCGxWpz1W6Y51Rc7XoD+Z3xCwtfeAwdsNqFqnxlijFk+gAITnp7fPDuHxQmNh2fIJ14FkUxQ6arRpN1CLoYzFqbKMFUf/kQZLBeN1CNfdx2MB1LKLJ+6CI2uiRhDvxd2Q7c6cRRRb6jKEHA0N9XaozPb6agJH/YMwJjWA4g4kjNv+37GaVUHvsA8YlTgQKBgQDR7zN6Xt63q5GASif/lak5Skh2POgZvonDVSU0ndaW5JwH7tvmK9Y5JvREqpsDaiTLkPumO52Bj3VwI4+W5wGZFSKLjYD7GPHlVGymVER58U/qyQ1260sUGYzDJqE6oCKARFjLDIIJAF3YerMHNdsxL2U7LlxwiYRuHLsZ25XMsQKBgQC0lCDIQCb2Qvh1magXb9AUUFKmg7VsHdAFjH6fh4Lb+pzjB+MFqBs3gV94/qd6ESxMwlf7B3IQdUq/y3gluRibwDvZJxe7n9YLmqxVSY+kOlXQFjwtEJRMrA7/PP6VIbwLMICJjuYr1esOhcpvJSX6YiBrN6YrhWytybrsQvYCPQKBgQDI+LgMNtP5etqAuP0PlSN6P92cq3P6aUiS4WkLtOpga5vfkE+dSmv7LIp9TeG15CAzbKksGzgOdtqTLsfgS2dhODVo6V8HgnrX8vlMRbGEF3pNJomoRVoLdOYnUEzaxcAhkMx8HY5CtcIhYl0O0wTyUhAUFq7R4SQREZogB/GhEQKBgQCz8eRVCLCKuCPVNKBvcmmcxIoICrmQGok/7kgD+sqpISyThDlzWph8kDwuWz1TA6fsecGokjvVMLyjMUPx+2gNVNuOwkkBG/71I1XVy28LTWJT8MRYd+wAF3j+Wa6rt6kmUVfVPAQb7JjGqQYYCaG1x3DcxuCc/+FTsPHHG9cAVQKBgQDNvtlgfv3xydc2g9VyBDrBGRoCR8QHJOCthslsyOj/oHhJKiXUrdu4f2OF9ua1R5CeEcEUhrrYKgB1j9rGxtXCOLjY7HXh1m0UgtvFNXFMFdAOnx1Uv3aqNrtIUm9wMIGycEZC1sOl4R6tAIVIrHIOLdWGofXTQQxYrkibw7dVag==";
//       String sign = "cGAgac1ypal1K3pLpNZURjWs1rMCtn4u0dPpvzhIZW8046G+D64xYqTTbk7C20GrCfNNGPA6ictKqbNC5gLfHPWcCmJ8ul80s+b05VPIhWoZoNZhAskPo/M8I/MwE3YuttnvjqncDyKV58KH8cSyUiyfpnaDIPJX14oBm0Y0paSU/bpvK/3mS6+GIqkad0cGEPkAOW2e3Mgr0VWxfVMM10o5E3SDgpkLzXj7mN1iZ3R5Bg9otGFSaSOwfz8TVaN7u3DublsJn/lDWKWxdHQ1nyWANzU64/nGCXjM6E+h+RgPmlRX1kXYPHrtX6SdQjrhnLlNqNOPeGIFz6fnHclVgg==";
//        verify(dataSource.getBytes(),pubKey,sign);
//        String sign = sign(dataSource.getBytes(), priKey);
//        System.out.println(sign);

        Map<String, Object> map = new HashMap<>();
        map.put("realName","董宗族");
        map.put("cardNo","330327199603311355");
        map.put("imgUrl","http://60.12.33.221:7343/res/group1/M00/00/0D/CgtSS2Bak9WAPk-_AAG5za-JyT047.jpeg");
        String sign=CertifyRSAUtils.encryptByPublicKey(JSON.toJSONString(map).getBytes(),pubKey);
        System.out.println(sign);
        String json=CertifyRSAUtils.decryptByPrivateKey(sign.getBytes(),priKey);
        System.out.println(json);

//        System.out.println(new String(decryptByPublicKey(sign.getBytes(),pubKey)));

        //Map<String, Object> map = RSAUtils.initKey();
        //System.out.println(RSAUtils.getPublicKey(map));
        //System.out.println(RSAUtils.getPrivateKey(map));


        //System.out.println(new String(RSAUtils.encryptByPublicKey("123456".getBytes(), "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAmhQZyX9MmaEIEOR0lYEpj+uGduGtg2kxn3LTOJBF0Ukb7KkYSt3OzmsS10wmXGwLT2sb84i6fzKPKDRM5gGLLPrxjkRz39DzNpz3cNyYzoyXQ9r7Dbz+aT4ikm57y9pZAPhTgRZkBmdcs5yqfsbjp0M1pKPaZzu37SZ++92v0bLbGYLzQG5eB2Anmcyi1Z4eUjTJhpIOuAW8a1+lg4MLbtERlY4Om8nQLmuRv5IxUMLsMLOeJPcVcjpbj4ITgUy69dqKEf3F/MA22LepSqzEZSxaKtpPfTqRXVudgo2wSynhtJZuZHA1mIeZn18lsu5Yb36VhEVmMh8WV/qLj31bKQIDAQAB")));

        //System.out.println(RSAUtils.decryptByPrivateKey(new String(RSAUtils.encryptByPublicKey("123456".getBytes(), "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAmhQZyX9MmaEIEOR0lYEpj+uGduGtg2kxn3LTOJBF0Ukb7KkYSt3OzmsS10wmXGwLT2sb84i6fzKPKDRM5gGLLPrxjkRz39DzNpz3cNyYzoyXQ9r7Dbz+aT4ikm57y9pZAPhTgRZkBmdcs5yqfsbjp0M1pKPaZzu37SZ++92v0bLbGYLzQG5eB2Anmcyi1Z4eUjTJhpIOuAW8a1+lg4MLbtERlY4Om8nQLmuRv5IxUMLsMLOeJPcVcjpbj4ITgUy69dqKEf3F/MA22LepSqzEZSxaKtpPfTqRXVudgo2wSynhtJZuZHA1mIeZn18lsu5Yb36VhEVmMh8WV/qLj31bKQIDAQAB")).getBytes(), "MIIEvQIBADANBgkqhkiG9w0BAQEFAASCBKcwggSjAgEAAoIBAQCaFBnJf0yZoQgQ5HSVgSmP64Z24a2DaTGfctM4kEXRSRvsqRhK3c7OaxLXTCZcbAtPaxvziLp/Mo8oNEzmAYss+vGORHPf0PM2nPdw3JjOjJdD2vsNvP5pPiKSbnvL2lkA+FOBFmQGZ1yznKp+xuOnQzWko9pnO7ftJn773a/RstsZgvNAbl4HYCeZzKLVnh5SNMmGkg64BbxrX6WDgwtu0RGVjg6bydAua5G/kjFQwuwws54k9xVyOluPghOBTLr12ooR/cX8wDbYt6lKrMRlLFoq2k99OpFdW52CjbBLKeG0lm5kcDWYh5mfXyWy7lhvfpWERWYyHxZX+ouPfVspAgMBAAECggEAe4NRiK17gkwMYz5AFvkLkEuF5xbSOV3CYcdyew1sanDo6bZDuwBEIETFPcHVnpEeH/QE3BznI8Ar8la6rkbegphHu3w0R18BGO6LjPXJqQoa2FJanpC6gTNGE4xtwOSwJI7sobaJSHjjXzhflHFrXVILVofjNI4yyvDpKo5zuu0e7R62idmCY02t0ZCbT0KIho+k9XahWtBjfuJI+2Fd2/VMyVWa/C3YRq+TzzeAvWKQMzuQL7xmoDl2lfsrA9veyPWaJcGMdANXq26KRfiSgxFxcxNze+1ND7CD2bobWWIpDx1MkyHYsFj0evCmn8NS0O7/oZQ2SYeLXUeoLyVs4QKBgQDPfJ2gEvfxEICYuBzX3O2OxrGI1ataZRQkqsEDbDi+oCYYQC+OYmFKd8r1RaPt0SNl/QT2A+NByV9VWb7M4p+g3EFaJ6fgANqtH/Agtyfx4LosVKtrHLR0xFEysy4LRoHAZucILnqUM/67+6EGGMlX4iVQW6HSTglkLDT87MqFWwKBgQC+GqpxbO0lhkBsxnv//RryIIsIOBN2X5DOhtukUwoW3j7e/N3WLDBnhMTkXc7X/8hP3uBX2PnVUDVQb5rOjrzWjupLk6il6Hv0YnrxiPDNZJPqh8PQOdlfx6MOfOVerR0RCpqQgUgtaIC73I342ekwotu+KJTNypm+7w+PEfeUywKBgDMa0IAy69r2Y5Y6EvCgO+rkyamYZ0I2j0WQwUWkjD13mKp0dMIvPSNoahcLdhsHXh6quWQXBQyZGuAc4L/6ObMXTQl1pwWncDUgHrcQJciPv4tXt3kEcvYnvJAuTfL9BMxtDA6Z1BtYyy9mBP1JcZ4x11r5ltwD2JBRyyNY93DtAoGBAJRbaRKVqEi1iceZ6qFvHD1DKl1ircWedPrIAdcuutv322bYPiNrXucalTGHB+pHgkLas4e7G9Xd/41jW5PBjxt+cthCRsYbNckVpc6jSRFxYYIRK75vtvVpbhXbYIFPZjSras5FJlfRi02QpPdE0BgVsJmpP9+OMgfwttq3cdibAoGAafYfh27cM+swa1iylxst/gtU7jyDv49eXIGegd/p1Oz3GofhUTV7rQtuD/wqbbK5Q679niEk6n6ZhAvr3HhQ3jjqdNa/Q2xCHIHcNRV+Frd7FZnifmcFoeotgwurFMdlSqcBVzbby4ZJyrG2iHxKKjMaHOmg9pXSdBciggdf1FQ="));
    }
}
