package com.joypatel.smalltasks.user.services;

import com.joypatel.smalltasks.user.dtos.UserResponse;
import com.joypatel.smalltasks.user.entities.User;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class UserHelperTests {

    private final UserHelper userHelper = new UserHelper();

    @Test
    void UserHelper_toResponse() {

        // given
        User user = new User();
        user.setRef("some-ref");
        user.setMobile("9872777736");
        user.setName("Ram Patel");
        
        // when
        UserResponse response = userHelper.toResponse(user);

        // then
        assertEquals(user.getRef(), response.getRef());
        assertEquals(user.getMobile(), response.getMobile());
        assertEquals(user.getName(), response.getName());
    }
}
