package com.joypatel.smalltasks.user.services;

import com.joypatel.smalltasks.user.dtos.UserResponse;
import com.joypatel.smalltasks.user.entities.User;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public boolean isUniqueMobile(String mobile) {
        return !userRepository.existsByMobile(mobile);
    }

    public UserResponse toResponse(User user) {

        if (user == null)
            return null;

        return UserResponse.builder()
                .ref(user.getRef())
                .mobile(user.getMobile())
                .name(user.getName())
                .pincode(user.getPincode())
                .build();
    }
}
