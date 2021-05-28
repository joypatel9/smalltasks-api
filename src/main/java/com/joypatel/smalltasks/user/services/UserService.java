package com.joypatel.smalltasks.user.services;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public boolean isUniqueMobile(String mobile) {
        return !userRepository.existsByMobile(mobile);
    }
}
