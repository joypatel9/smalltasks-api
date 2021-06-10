package com.joypatel.smalltasks.user.services;

import com.joypatel.smalltasks.user.dtos.UserResponse;
import com.joypatel.smalltasks.user.entities.User;
import org.springframework.stereotype.Component;

@Component
class UserHelper {

    UserResponse toResponse(User user) {

        return UserResponse.builder()
                .ref(user.getRef())
                .mobile(user.getMobile())
                .name(user.getName())
                .pincode(user.getPincode())
                .build();
    }
}
