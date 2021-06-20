package com.joypatel.smalltasks.common;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.orm.ObjectOptimisticLockingFailureException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.validation.ConstraintViolationException;
import java.util.Set;
import java.util.stream.Collectors;

@RestControllerAdvice
@Slf4j
@AllArgsConstructor
public class MyControllerAdvice {

    private final MyUtils utils;

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @RequestMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
    public Set<MyError> handleException(MethodArgumentNotValidException ex) {

        var errors = ex.getAllErrors().stream()
                .map(MyError::of)
                .collect(Collectors.toSet());

        log.info("MethodArgumentNotValidException: {}", errors);
        return errors;
    }

    @ExceptionHandler(ConstraintViolationException.class)
    @RequestMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
    public Set<MyError> handleException(ConstraintViolationException ex) {

        var errors = ex.getConstraintViolations().stream()
                .map(MyError::of)
                .collect(Collectors.toSet());

        log.info("ConstraintViolationException: {}", errors);
        return errors;
    }

    @ExceptionHandler(BusinessException.class)
    @RequestMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Set<MyError>> handleException(BusinessException ex) {

        log.info("BusinessException {}: {}", ex.getResponseStatus(), ex.getErrors());
        return new ResponseEntity<>(ex.getErrors(), ex.getResponseStatus());
    }

    @ExceptionHandler(ObjectOptimisticLockingFailureException.class)
    @RequestMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CONFLICT)
    public Set<MyError> handleException(ObjectOptimisticLockingFailureException ex) {

        log.info("ObjectOptimisticLockingFailureException: {}", ex.getMessage());
        return Set.of(MyError.of(null, "optimisticLockFailure",
                utils.getMessage("optimisticLockFailure")));
    }
}
