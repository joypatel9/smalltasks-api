package com.joypatel.smalltasks.task.validation;

import javax.validation.Constraint;
import javax.validation.Payload;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.lang.annotation.Retention;

import static java.lang.annotation.RetentionPolicy.RUNTIME;

@NotBlank
@Size(max = 30)
@Retention(RUNTIME)
@Constraint(validatedBy = {})
public @interface ValidSubject {

    String message() default "";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
