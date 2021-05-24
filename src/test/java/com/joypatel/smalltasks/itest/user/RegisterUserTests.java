package com.joypatel.smalltasks.itest.user;

import com.joypatel.smalltasks.itest.AbstractMvcTests;
import com.joypatel.smalltasks.user.entities.User;
import org.apache.commons.io.IOUtils;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.List;

import static com.joypatel.smalltasks.common.MyUtils.UUID_LEN;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class RegisterUserTests extends AbstractMvcTests {

    @Autowired
    private TestUserRepository repository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    private String registerUserPayload;

    @Value("classpath:itest/user/register-user.json")
    public void setRegisterUserPayload(Resource resource) throws IOException {
        registerUserPayload = IOUtils.toString(resource.getInputStream(), StandardCharsets.UTF_8);
    }

    private String invalidData;

    @Value("classpath:itest/user/register-user-invalid-data.json")
    public void setInvalidData(Resource resource) throws IOException {
        invalidData = IOUtils.toString(resource.getInputStream(), StandardCharsets.UTF_8);
    }

    @Test
    void testRegisterUser_When_InvalidData() throws Exception {

        // when
        mockMvc.perform(post("/users")
                .contentType(MediaType.APPLICATION_JSON)
                .content(invalidData))

                // then
                .andExpect(status().isUnprocessableEntity())

                .andExpect(jsonPath("$[0].field").value("register.form.name"))
                .andExpect(jsonPath("$[0].code").value("{javax.validation.constraints.NotBlank.message}"))
                .andExpect(jsonPath("$[0].message").value("Please provide a value"))

                .andExpect(jsonPath("$[1].field").value("register.form.mobile"))
                .andExpect(jsonPath("$[1].code").value("{exactSize}"))
                .andExpect(jsonPath("$[1].message").value("Please enter 10 chars"))

                .andExpect(jsonPath("$[2].field").value("register.form.password"))
                .andExpect(jsonPath("$[2].code").value("{javax.validation.constraints.Size.message}"))
                .andExpect(jsonPath("$[2].message").value("Please enter 8 to 32 chars"));

        List<User> users = repository.findAll();
        assertEquals(0, users.size());
    }

    @Test
    void testRegisterUser() throws Exception {

        // given
        var name = "Joy Patel";
        var mobile = "9999999999";
        var password = "password";

        // when
        mockMvc.perform(post("/users")
                .contentType(MediaType.APPLICATION_JSON)
                .content(registerUserPayload))

                // then
                .andExpect(status().isCreated())
                .andExpect(jsonPath("ref").isString())
                .andExpect(jsonPath("name").value(name))
                .andExpect(jsonPath("mobile").value(mobile));

        List<User> users = repository.findAll();
        assertEquals(1, users.size());

        User user = users.get(0);
        assertNotNull(user.getId());
        assertEquals(UUID_LEN, user.getRef().length());
        assertEquals(mobile, user.getMobile());
        assertEquals(name, user.getName());
        assertTrue(passwordEncoder.matches(password, user.getPassword()));
    }


}
