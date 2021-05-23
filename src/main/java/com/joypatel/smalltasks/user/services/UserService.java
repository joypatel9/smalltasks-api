package com.joypatel.smalltasks.user.services;

import com.joypatel.smalltasks.user.dtos.RegisterForm;
import com.joypatel.smalltasks.user.dtos.UserResponse;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import javax.validation.Valid;

@Service
@Validated
public class UserService {

    public UserResponse register(@Valid RegisterForm form) {
        return null;
    }
}
