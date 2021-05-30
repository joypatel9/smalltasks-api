package com.joypatel.smalltasks.common;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.util.Set;
import java.util.stream.Collectors;

@RestControllerAdvice
public class MyControllerAdvice {

    @AllArgsConstructor
    @Getter
    public static class Error {

        private final String field;
        private final String code;
        private final String message;

        public static Error of(ObjectError error) {
            return new Error(
                    error instanceof FieldError ?  ((FieldError)error).getField() : error.getObjectName(),
                    error.getCode(),
                    error.getDefaultMessage());
        }

        public static Error of(ConstraintViolation<?> error) {
            return new Error(
                    error.getPropertyPath().toString().split("\\.")[2],
                    error.getMessageTemplate(),
                    error.getMessage());
        }
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @RequestMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
    public Set<Error> handleException(MethodArgumentNotValidException ex) {

        return ex.getAllErrors().stream()
                .map(Error::of)
                .collect(Collectors.toSet());
    }

    @ExceptionHandler(ConstraintViolationException.class)
    @RequestMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
    public Set<Error> handleException(ConstraintViolationException ex) {
        return ex.getConstraintViolations().stream()
                .map(Error::of)
                .collect(Collectors.toSet());
    }

}
