package com.joypatel.smalltasks.user.validation;

import com.joypatel.smalltasks.user.services.UserService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

@Component
@AllArgsConstructor
public class UniqueMobileValidator implements ConstraintValidator<UniqueMobile, String> {

    private final UserService userService;

    @Override
    public boolean isValid(String mobile, ConstraintValidatorContext context) {
        return userService.isUniqueMobile(mobile);
    }
}
