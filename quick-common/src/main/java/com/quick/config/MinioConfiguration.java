package com.quick.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.security.PrivateKey;

/**
 * @author dzz
 */
@Data
@Component
@ConfigurationProperties(prefix = "minio")
public class MinioConfiguration {

    private String url;

    private String port;

    private String accessKey;

    private String secretKey;

    private String bucketName;

    private String baceUrl;

}