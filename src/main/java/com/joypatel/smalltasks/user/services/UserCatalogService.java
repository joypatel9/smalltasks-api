package com.joypatel.smalltasks.user.services;

import com.joypatel.smalltasks.common.MyUtils;
import com.joypatel.smalltasks.user.dtos.UserResponse;
import com.joypatel.smalltasks.user.entities.User;
import lombok.AllArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class UserCatalogService {

    private final UserRepository userRepository;
    private final UserHelper userHelper;

    @PreAuthorize("isAuthenticated()")
    public UserResponse getCurrentUser() {

        Integer userId = MyUtils.getCurrentUser().getId();
        User user = userRepository.getById(userId);
        return userHelper.toResponse(user);
    }
}
