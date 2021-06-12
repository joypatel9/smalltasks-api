package com.joypatel.smalltasks.user.services;

import com.joypatel.smalltasks.user.dtos.UserResponse;
import com.joypatel.smalltasks.user.entities.User;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
class UserServiceTests {

    @InjectMocks
    private UserService userService;

    @Test
    void UserService_toResponse() {

        // given
        User user = new User();
        user.setRef("some-ref");
        user.setMobile("9872777736");
        user.setName("Joy Patel");
        user.setPincode(148392);

        // when
        UserResponse response = userService.toResponse(user);

        // then
        assertEquals(user.getRef(), response.getRef());
        assertEquals(user.getMobile(), response.getMobile());
        assertEquals(user.getName(), response.getName());
        assertEquals(user.getPincode(), response.getPincode());
    }
}
