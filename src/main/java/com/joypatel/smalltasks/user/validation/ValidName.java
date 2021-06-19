package com.joypatel.smalltasks.user.validation;

import javax.validation.Constraint;
import javax.validation.Payload;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.lang.annotation.Retention;

import static java.lang.annotation.RetentionPolicy.RUNTIME;

@NotBlank
@Size(max = 100)
@Retention(RUNTIME)
@Constraint(validatedBy = {})
public @interface ValidName {

    String message() default "";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
