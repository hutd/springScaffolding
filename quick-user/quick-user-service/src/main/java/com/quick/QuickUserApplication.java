package com.quick;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

/**
 * 启动类
 */
@SpringBootApplication
@EnableFeignClients
@EnableDiscoveryClient
public class QuickUserApplication {

    public static void main(String[] args) {
        SpringApplication.run(QuickUserApplication.class, args);
    }

//    /**
//     * 启动时创建RestTemplate
//     * 使用@LoadBalanced标识负载均衡，默认轮询
//     */
//    @Bean
//    @LoadBalanced
//    public RestTemplate restTemplate() {
//        return new RestTemplate();
//    }
}
