package com.quick.utils;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.security.*;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;



public class RsaUtils {

    /**
     * 从文件中读取公钥
     *
     * @param fileName
     * @return
     * @throws Exception
     */
    public static PublicKey getPublicKey(String fileName) throws Exception {
        byte[] bytes = readFile(fileName);
        return getPublicKey(bytes);
    }

    /**
     * 从文件中读取私钥
     * @param fileName
     * @return
     * @throws Exception
     */
    public static PrivateKey getPrivateKey(String fileName) throws Exception{
        byte[] bytes = readFile(fileName);
        return getPrivateKey(bytes);
    }

    /**
     * 获取公钥
     *
     * @param bytes
     * @return
     * @throws Exception
     */
    public static PublicKey getPublicKey(byte[] bytes) throws Exception {
        X509EncodedKeySpec spec = new X509EncodedKeySpec(bytes);
        KeyFactory factory = KeyFactory.getInstance("RSA");
        return factory.generatePublic(spec);
    }

    /**
     * 获取私钥
     *
     * @param bytes
     * @return
     * @throws Exception
     */
    public static PrivateKey getPrivateKey(byte[] bytes) throws Exception {
        PKCS8EncodedKeySpec spec = new PKCS8EncodedKeySpec(bytes);
        KeyFactory factory = KeyFactory.getInstance("RSA");
        return factory.generatePrivate(spec);
    }

    /**
     * 读取文件
     *
     * @param fileName
     * @return
     * @throws IOException
     */
    public static byte[] readFile(String fileName) throws IOException {
        return Files.readAllBytes(new File(fileName).toPath());

    }

    /**
     * 根据密文，生存rsa公钥和私钥,并写入指定文件
     *
     * @param publicKeyFilename  公钥文件路径
     * @param privateKeyFilename 私钥文件路径
     * @param secret             生成密钥的密文
     * @throws IOException
     * @throws NoSuchAlgorithmException
     */
    public static void generateKey(String publicKeyFilename, String privateKeyFilename, String secret) throws Exception {
        KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("RSA");
        SecureRandom secureRandom = new SecureRandom(secret.getBytes());
        keyPairGenerator.initialize(2048, secureRandom);
        KeyPair keyPair = keyPairGenerator.genKeyPair();
        // 获取公钥并写出
        byte[] publicKeyBytes = keyPair.getPublic().getEncoded();
        writeFile(publicKeyFilename, publicKeyBytes);
        // 获取私钥并写出
        byte[] privateKeyBytes = keyPair.getPrivate().getEncoded();
        writeFile(privateKeyFilename, privateKeyBytes);
    }

    /**
     * 向目标路径写入文件
     *
     * @param destPath
     * @param bytes
     * @throws IOException
     */
    public static void writeFile(String destPath, byte[] bytes) throws IOException {
        File dest = new File(destPath);
        if (!dest.exists()) {
            dest.createNewFile();
        }
        Files.write(dest.toPath(), bytes);
    }

    /**
     * 获取token
     * @param content
     * @param publicKey
     * @return
     * @throws NoSuchPaddingException
     * @throws NoSuchAlgorithmException
     * @throws InvalidKeyException
     * @throws BadPaddingException
     * @throws IllegalBlockSizeException
     */
    public static String greateToken(String content, PublicKey publicKey) throws NoSuchPaddingException, NoSuchAlgorithmException, InvalidKeyException, BadPaddingException, IllegalBlockSizeException {
        Cipher cipher = Cipher.getInstance("RSA");
        cipher.init(Cipher.ENCRYPT_MODE, publicKey);
        byte[] bytes = cipher.doFinal(content.getBytes());

        return com.quick.utils.Base64.encodeToString(bytes);
    }

    /**
     * 解析token
     * @param token
     * @param privateKey
     * @return
     * @throws IOException
     * @throws NoSuchPaddingException
     * @throws NoSuchAlgorithmException
     * @throws InvalidKeyException
     * @throws BadPaddingException
     * @throws IllegalBlockSizeException
     */
   public static String parseToken(String token,PrivateKey privateKey) throws IOException, NoSuchPaddingException, NoSuchAlgorithmException, InvalidKeyException, BadPaddingException, IllegalBlockSizeException {
       byte[] tokenByte = com.quick.utils.Base64.decode(token);
       Cipher cipher = Cipher.getInstance("RSA");
       cipher.init(Cipher.DECRYPT_MODE, privateKey);
       byte[] bytes = cipher.doFinal(tokenByte);
       return new String(bytes);
   }


//   public static String greateToken(UserInfo userInfo, PublicKey publicKey) throws IllegalBlockSizeException, InvalidKeyException, BadPaddingException, NoSuchAlgorithmException, NoSuchPaddingException {
//        String content= JsonUtils.toString(userInfo);
//
//       return greateToken(content,publicKey);
//
//   }




    /**
     * 获取用户信息
     * @param token
     * @param privateKey
     * @return
     * @throws NoSuchPaddingException
     * @throws NoSuchAlgorithmException
     * @throws IOException
     * @throws BadPaddingException
     * @throws IllegalBlockSizeException
     * @throws InvalidKeyException
     */
//   public static UserInfo getUserInfo(String token,PrivateKey privateKey) throws NoSuchPaddingException, NoSuchAlgorithmException, IOException, BadPaddingException, IllegalBlockSizeException, InvalidKeyException {
//      String userInfoJson = parseToken(token,privateKey);
//      UserInfo userInfo =  JsonUtils.toBean(userInfoJson,UserInfo.class);;
//
//      return userInfo;
//   }


    private static final String pubKeyPath = "E:\\rsa.pub";

    private static final String priKeyPath = "E:\\rsa.pri";

    public static void main(String[] args) throws Exception {

//        String source = "dddddddddddddddddddddddddddddddddddddd";
//
//        PublicKey publicKey = RsaUtils.getPublicKey(pubKeyPath);
//
//        String token = greateToken(source,publicKey);
//
//        System.out.println("公钥加密并Base64编码的结果：" + token);
//
//        PrivateKey privateKey = RsaUtils.getPrivateKey(priKeyPath);
//
//        System.out.println("解密后的明文: " + parseToken(token,privateKey));

//        String pubKeyPath = "E:\\rsa.pub";
//        PublicKey publicKey = RsaUtils.getPublicKey(pubKeyPath);
//        UserInfo userInfo = new UserInfo("20", "jack");
//        String userInfoJson = JsonUtils.toString(userInfo);

//        // 生成token
//        String token = RsaUtils.greateToken(userInfoJson,publicKey);
//        System.out.println("token = " + token);
//
//
//        PrivateKey privateKey = RsaUtils.getPrivateKey(priKeyPath);
//
//        System.out.println("解密后的明文: " + parseToken(token,privateKey));
    }























}
