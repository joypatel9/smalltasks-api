package com.joypatel.smalltasks.common;

import com.joypatel.smalltasks.common.security.MyUserDetails;
import lombok.AllArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
@AllArgsConstructor
public class MyUtils {

    public static final int UUID_LEN = 36;

    private final MessageSource messageSource;

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

    public String newUid() {
        return UUID.randomUUID().toString();
    }

    /**
     * Gets a message from messages.properties
     */
    public String getMessage(String messageKey, Object... args) {
        return messageSource.getMessage(messageKey, args, LocaleContextHolder.getLocale());
    }


}
