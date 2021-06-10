package com.joypatel.smalltasks.user.controllers;

import com.joypatel.smalltasks.user.dtos.RegisterForm;
import com.joypatel.smalltasks.user.dtos.UserResponse;
import com.joypatel.smalltasks.user.services.UserCatalogService;
import com.joypatel.smalltasks.user.services.UserRegistrationService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
@AllArgsConstructor
public class UserController {

    private final UserRegistrationService userRegistrationService;
    private final UserCatalogService userCatalogService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public UserResponse register(@RequestBody RegisterForm form) {
        return userRegistrationService.register(form);
    }

    @GetMapping("/me")
    public UserResponse getUser() {
        return userCatalogService.getCurrentUserResponse();
    }

}
