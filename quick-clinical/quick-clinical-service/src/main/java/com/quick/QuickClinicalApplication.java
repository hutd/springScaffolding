package com.quick;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
@EnableFeignClients
public class QuickClinicalApplication {

    public static void main(String[] args) {
        SpringApplication.run(QuickClinicalApplication.class, args);
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
