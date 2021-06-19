package com.joypatel.smalltasks.user.services;

import com.joypatel.smalltasks.common.MyUtils;
import com.joypatel.smalltasks.user.dtos.RegisterForm;
import com.joypatel.smalltasks.user.dtos.UserResponse;
import com.joypatel.smalltasks.user.entities.User;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import javax.validation.Valid;

@Service
@Validated
@AllArgsConstructor
@Slf4j
public class UserRegistrationService {

    private final UserRepository userRepository;
    private final UserService userService;
    private final PasswordEncoder passwordEncoder;
    private final MyUtils utils;

    public UserResponse register(@Valid RegisterForm form) {

        log.info("Registering user {}", form);

        User user = toUser(form);
        userRepository.save(user);

        UserResponse response = userService.toResponse(user);
        log.info("Registered user {}", response);
        return response;
    }

    private User toUser(RegisterForm form) {

        User user = new User();
        user.setRef(utils.newUid());
        user.setMobile(form.getMobile());
        user.setName(form.getName());
        user.setPassword(passwordEncoder.encode(form.getPassword()));
        user.setPincode(form.getPincode());

        return user;
    }
}
