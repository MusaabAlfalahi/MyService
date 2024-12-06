package net.shahid.microservices.myservice.utils;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Setter
@Getter
@Component
@ConfigurationProperties("redis.keys")
public class RedisKeys {
    private String defaultKey;
    private String customKey;
}
