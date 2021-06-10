package com.joypatel.smalltasks;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.joypatel.smalltasks.user.dtos.AuthToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.transaction.annotation.Transactional;

import static com.joypatel.smalltasks.user.TestUtils.MOBILE;
import static com.joypatel.smalltasks.user.TestUtils.PASSWORD;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
@ActiveProfiles("test")
public abstract class AbstractMvcTests {

    @Autowired
    protected ObjectMapper objectMapper;

    @Autowired
    protected MockMvc mockMvc;

    protected ResultActions login(String mobile, String password) throws Exception {
        return mockMvc.perform(post("/login")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED_VALUE)
                .accept(MediaType.APPLICATION_JSON)
                .param("username", mobile)
                .param("password", password));
    }

    protected String getToken(String mobile, String password) throws Exception {

        var result = login(MOBILE, PASSWORD).andReturn();

        String body = result.getResponse().getContentAsString();
        AuthToken token = objectMapper.readValue(body, AuthToken.class);
        return token.getToken();
    }

}
