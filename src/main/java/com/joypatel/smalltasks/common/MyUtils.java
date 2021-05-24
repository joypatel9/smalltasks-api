package com.joypatel.smalltasks.common;

import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class MyUtils {

    public static final int UUID_LEN = 36;

    public String newUid() {
        return UUID.randomUUID().toString();
    }
}
