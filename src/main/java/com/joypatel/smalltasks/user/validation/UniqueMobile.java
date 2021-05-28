package com.joypatel.smalltasks.user.validation;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Retention;

import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Retention(RUNTIME)
@Constraint(validatedBy = {UniqueMobileValidator.class})
public @interface UniqueMobile {

    String message() default "{mobileNotUnique}";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
