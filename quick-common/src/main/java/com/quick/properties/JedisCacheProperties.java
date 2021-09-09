package com.quick.properties;

import com.google.common.collect.Maps;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.time.Duration;
import java.util.Map;

@Data
@ConfigurationProperties(prefix = "quick.jedis-caches")
public class JedisCacheProperties {

    private final Map<String, Duration> cacheNames = Maps.newHashMap();
    private boolean cacheEnable = true;

    public boolean isCacheEnable() {
        return cacheEnable;
    }

    public Map<String, Duration> getCacheNames() {

        return cacheNames;
    }

}
