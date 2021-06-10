package com.joypatel.smalltasks.user.validation;


import org.hibernate.validator.constraints.Range;

import javax.validation.Constraint;
import javax.validation.Payload;
import javax.validation.constraints.NotNull;
import java.lang.annotation.Retention;

import static com.joypatel.smalltasks.user.entities.User.PINCODE_MAX;
import static com.joypatel.smalltasks.user.entities.User.PINCODE_MIN;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@NotNull
@Range(min = PINCODE_MIN, max = PINCODE_MAX, message = "{pincodeRange}")
@Retention(RUNTIME)
@Constraint(validatedBy = {})
public @interface ValidPincode {

    String message() default "";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
