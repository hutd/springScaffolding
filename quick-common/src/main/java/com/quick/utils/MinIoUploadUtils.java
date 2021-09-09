package com.quick.utils;

import com.quick.enums.ExceptionEnum;
import com.quick.exception.CamelliaException;
import io.minio.MinioClient;
import io.minio.errors.InvalidEndpointException;
import io.minio.errors.InvalidPortException;
import io.minio.policy.PolicyType;

import java.io.InputStream;


public class MinIoUploadUtils {


    /**
     * 获取MinioClient
     *
     * @return
     * @throws InvalidPortException
     * @throws InvalidEndpointException
     */
    public static MinioClient minioClient(String url, String accessKey, String secretKey) throws InvalidPortException, InvalidEndpointException {
        return new MinioClient(url, accessKey, secretKey);
    }

    /**
     * minio文件上传
     *
     * @param bucketName  存储桶
     * @param fileName    文件名
     * @param inputStream 输入流
     * @param contentType 文件类型
     * @param size        文件大小
     */
    public static String uploadFile(MinioClient client, String bucketName, String fileName, InputStream inputStream, String contentType, long size) {
        try {
            if (!client.bucketExists(bucketName)) {
                client.makeBucket(bucketName);
                // 设置存储桶策略
                client.setBucketPolicy(bucketName, "*", PolicyType.READ_ONLY);
            }
            client.putObject(bucketName, fileName, inputStream, size, contentType);
            return client.getObjectUrl(bucketName, fileName);
        } catch (Exception e) {
            throw new CamelliaException(ExceptionEnum.UPLOAD_FAILED);
        }
    }

    /**
     *
     * @param client      文件服务器
     * @param bucketName  存储桶
     * @param filename  文件名
     * @return
     */
    public static String removeFile(MinioClient client, String bucketName, String filename) {
        if (bucketName.length() != 0 && filename.length() != 0) {
            try {
                client.removeObject(bucketName, filename);
            } catch (Exception e) {
                throw new CamelliaException(ExceptionEnum.DEL_FAILED);
            }
        }
        return ExceptionEnum.DEL_SUCCESS.toString();
    }
}
