package com.app.socialmediaapi.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "app.secret")
@Data
public class AppSecret {
    private String key;
    private Long lifetime;
}
