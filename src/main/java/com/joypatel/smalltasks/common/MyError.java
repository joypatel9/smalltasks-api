package com.joypatel.smalltasks.common;

import lombok.Data;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;

import javax.validation.ConstraintViolation;

@Data
public class MyError {

    private final String field;
    private final String code;
    private final String message;

    public static MyError of(ObjectError error) {
        return MyError.of(
                error instanceof FieldError ? ((FieldError) error).getField() : error.getObjectName(),
                error.getCode(),
                error.getDefaultMessage());
    }

    public static MyError of(ConstraintViolation<?> error) {
        return MyError.of(
                error.getPropertyPath().toString().split("\\.")[2],
                error.getMessageTemplate(),
                error.getMessage());
    }

    public static MyError of(String field, String code, String message) {
        return new MyError(field, code, message);
    }
}
