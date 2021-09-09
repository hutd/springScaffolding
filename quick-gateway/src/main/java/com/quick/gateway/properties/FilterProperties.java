package com.quick.gateway.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.List;


@Data
@ConfigurationProperties(prefix = "quick.filter")
public class FilterProperties {

    private List<String> allowPaths;

}