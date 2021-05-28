package com.joypatel.smalltasks.user.validation;

import javax.validation.Constraint;
import javax.validation.Payload;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.lang.annotation.Retention;

import static com.joypatel.smalltasks.user.entities.User.MOBILE_LEN;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@NotBlank
@Size(min = MOBILE_LEN, max = MOBILE_LEN, message="{exactSize}")
@Pattern(regexp = "^[0-9]+$")
@Retention(RUNTIME)
@Constraint(validatedBy = { })
public @interface ValidMobile {

    String message() default "";
    Class<?>[] groups() default { };
    Class<? extends Payload>[] payload() default { };
}
