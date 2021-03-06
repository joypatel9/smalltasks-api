package com.joypatel.smalltasks.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@Slf4j
public class WebMvcConfig implements WebMvcConfigurer {

    @Value("${web.url:http://localhost:9000}")
    private String webUrl;

    @Override
    public void addCorsMappings(CorsRegistry registry) {

        log.info("Configuring CorsMappings with webUrl {}", webUrl);

        registry
                .addMapping("/**")
                .allowedOrigins(webUrl);
    }
}
