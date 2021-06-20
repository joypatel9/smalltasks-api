package com.joypatel.smalltasks.common;

import lombok.Builder;
import lombok.Getter;
import lombok.Singular;
import org.springframework.http.HttpStatus;

import java.util.Set;

@Builder
@Getter
public class BusinessException extends RuntimeException {

    private final HttpStatus responseStatus;

    @Singular
    private final Set<MyError> errors;
}
