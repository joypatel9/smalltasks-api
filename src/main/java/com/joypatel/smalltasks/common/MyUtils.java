package com.joypatel.smalltasks.common;

import com.joypatel.smalltasks.common.security.MyUserDetails;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class MyUtils {

    public static final int UUID_LEN = 36;

    public String newUid() {
        return UUID.randomUUID().toString();
    }

    public static MyUserDetails getCurrentUser(Authentication auth) {

        if (auth != null) {
            Object principal = auth.getPrincipal();
            if (principal instanceof MyUserDetails) {
                return (MyUserDetails) principal;
            }
        }
        return null;
    }

    public static MyUserDetails getCurrentUser() {
        return getCurrentUser(SecurityContextHolder.getContext().getAuthentication());
    }


}
