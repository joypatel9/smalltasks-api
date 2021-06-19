package com.joypatel.smalltasks;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class SmalltasksApiApplication {

    public static void main(String[] args) {
        SpringApplication.run(SmalltasksApiApplication.class, args);
    }

}
