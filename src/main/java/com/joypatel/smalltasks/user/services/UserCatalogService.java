package com.joypatel.smalltasks.user.services;

import com.joypatel.smalltasks.common.MyUtils;
import com.joypatel.smalltasks.user.dtos.UserResponse;
import com.joypatel.smalltasks.user.entities.User;
import lombok.AllArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@AllArgsConstructor
public class UserCatalogService {

    private final UserRepository userRepository;
    private final UserService userService;

    @PreAuthorize("isAuthenticated()")
    @Transactional(readOnly = true)
    public UserResponse getCurrentUserResponse() {
        User user = getCurrentUser();
        return userService.toResponse(user);
    }

    public User getCurrentUser() {
        Integer userId = MyUtils.getCurrentUser().getId();
        return userRepository.getById(userId);
    }
}
