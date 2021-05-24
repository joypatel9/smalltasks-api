package com.joypatel.smalltasks.common.validation;

import javax.validation.Constraint;
import javax.validation.Payload;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.lang.annotation.Retention;

import static com.joypatel.smalltasks.user.entities.User.PASSWORD_MAX;
import static com.joypatel.smalltasks.user.entities.User.PASSWORD_MIN;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@NotBlank
@Size(min = PASSWORD_MIN, max = PASSWORD_MAX)
@Retention(RUNTIME)
@Constraint(validatedBy = { })
public @interface ValidPassword {

    String message() default "";
    Class<?>[] groups() default { };
    Class<? extends Payload>[] payload() default { };
}
