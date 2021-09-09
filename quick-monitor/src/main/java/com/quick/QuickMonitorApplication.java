package com.quick;

import de.codecentric.boot.admin.server.config.EnableAdminServer;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

@EnableAdminServer
@SpringBootApplication
@EnableDiscoveryClient
@EnableFeignClients
public class QuickMonitorApplication {

    public static void main(String[] args) {
        SpringApplication.run(QuickMonitorApplication.class, args);
    }

}
