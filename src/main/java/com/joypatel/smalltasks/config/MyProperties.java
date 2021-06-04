package com.joypatel.smalltasks.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.validation.annotation.Validated;

@Validated
@ConfigurationProperties(prefix = "smalltasks")
@Getter
@Setter
@Configuration
public class MyProperties {

    private String jwtSecret;
}
