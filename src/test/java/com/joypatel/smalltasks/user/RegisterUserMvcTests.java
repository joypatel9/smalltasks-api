package com.joypatel.smalltasks.user;

import com.joypatel.smalltasks.AbstractMvcTests;
import com.joypatel.smalltasks.user.entities.User;
import org.apache.commons.io.IOUtils;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.jdbc.Sql;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.List;

import static com.joypatel.smalltasks.common.MyUtils.UUID_LEN;
import static com.joypatel.smalltasks.user.TestUtils.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class RegisterUserMvcTests extends AbstractMvcTests {

    @Autowired
    private TestUserRepository repository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    private String registerUserData;
    private String invalidData;

    @Value("classpath:itest/user/payload/register-user.json")
    public void setRegisterUserData(Resource resource) throws IOException {
        registerUserData = IOUtils.toString(resource.getInputStream(), StandardCharsets.UTF_8);
    }

    @Value("classpath:itest/user/payload/register-user-invalid-data.json")
    public void setInvalidData(Resource resource) throws IOException {
        invalidData = IOUtils.toString(resource.getInputStream(), StandardCharsets.UTF_8);
    }

    @Test
    void registerUser_When_InvalidData() throws Exception {

        // when
        mockMvc.perform(post("/users")
                .contentType(MediaType.APPLICATION_JSON)
                .content(invalidData))

                // then
                .andExpect(status().isUnprocessableEntity());

        List<User> users = repository.findAll();
        assertEquals(0, users.size());
    }

    @Test
    void registerUser() throws Exception {

        // when
        mockMvc.perform(post("/users")
                .contentType(MediaType.APPLICATION_JSON)
                .content(registerUserData))

                // then
                .andExpect(status().isCreated())
                .andExpect(jsonPath("ref").isString())
                .andExpect(jsonPath("name").value(NAME))
                .andExpect(jsonPath("mobile").value(MOBILE))
                .andExpect(jsonPath("pincode").value(PINCODE));

        List<User> users = repository.findAll();
        assertEquals(1, users.size());

        User user = users.get(0);
        assertNotNull(user.getId());
        assertEquals(UUID_LEN, user.getRef().length());
        assertEquals(MOBILE, user.getMobile());
        assertEquals(NAME, user.getName());
        assertTrue(passwordEncoder.matches(PASSWORD, user.getPassword()));
        assertEquals(PINCODE, user.getPincode());
    }

    @Test
    @Sql("classpath:itest/user/sql/user.sql")
    void registerUser_When_MobileAlreadyUsed() throws Exception {

        // when
        mockMvc.perform(post("/users")
                .contentType(MediaType.APPLICATION_JSON)
                .content(registerUserData))

                // then
                .andExpect(status().isUnprocessableEntity())
                .andExpect(jsonPath("$[0].field").value("mobile"))
                .andExpect(jsonPath("$[0].code").value("{mobileNotUnique}"))
                .andExpect(jsonPath("$[0].message").exists());
    }
}
