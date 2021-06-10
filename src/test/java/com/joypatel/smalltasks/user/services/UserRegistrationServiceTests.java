package com.joypatel.smalltasks.user.services;

import com.joypatel.smalltasks.common.MyUtils;
import com.joypatel.smalltasks.user.dtos.RegisterForm;
import com.joypatel.smalltasks.user.dtos.UserResponse;
import com.joypatel.smalltasks.user.entities.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserRegistrationServiceTests {

    private final RegisterForm form = new RegisterForm();
    private final UserResponse response = UserResponse.builder().build();

    @Mock
    private UserRepository userRepository;

    @Mock
    private UserHelper userHelper;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private MyUtils utils;

    @InjectMocks
    private UserRegistrationService service;

    @Captor
    private ArgumentCaptor<User> userCaptor;

    @BeforeEach
    void setUp() {

        form.setMobile("7835226756");
        form.setName("Joy Patel");
        form.setPassword("password");
        form.setPincode(473846);
    }

    @Test
    void testRegister() {

        // given
        String encodedPassword = "top-secret-encoded";
        String ref = "ref-43535if9";

        when(passwordEncoder.encode(form.getPassword())).thenReturn(encodedPassword);
        when(utils.newUid()).thenReturn(ref);
        when(userHelper.toResponse(any(User.class))).thenReturn(response);

        // when
        UserResponse r = service.register(form);

        // then
        assertEquals(response, r);

        verify(userRepository).save(userCaptor.capture());
        User user = userCaptor.getValue();
        verify(userHelper).toResponse(user);

        assertEquals(ref, user.getRef());
        assertEquals(form.getMobile(), user.getMobile());
        assertEquals(form.getName(), user.getName());
        assertEquals(encodedPassword, user.getPassword());
        assertEquals(form.getPincode(), user.getPincode());
    }
}
