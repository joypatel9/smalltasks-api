package com.joypatel.smalltasks.common;

import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class MyUtils {

    public String newUid() {
        return UUID.randomUUID().toString();
    }
}
