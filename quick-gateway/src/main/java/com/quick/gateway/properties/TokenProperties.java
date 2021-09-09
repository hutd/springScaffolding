package com.quick.gateway.properties;


import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.properties.ConfigurationProperties;


@Slf4j
@Data
@ConfigurationProperties(prefix = "quick.login")
public class TokenProperties {

    private Long tokenExpire;

}
