package com.joypatel.smalltasks.user;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.joypatel.smalltasks.AbstractMvcTests;
import com.joypatel.smalltasks.config.MyProperties;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.ResultActions;

import java.util.Date;

import static com.joypatel.smalltasks.config.AuthTokenFilter.TOKEN_PREFIX;
import static com.joypatel.smalltasks.user.TestUtils.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

 class LoginMvcTests extends AbstractMvcTests {

     @Autowired
     private ObjectMapper objectMapper;

     @Autowired
     private MyProperties properties;

     @Test
     @Sql("classpath:itest/user/sql/user.sql")
     void login_Should_Respond401_When_InvalidPassword() throws Exception {

         login(MOBILE, "password2")
                 .andExpect(status().isUnauthorized());
     }

     @Test
     void getUser_Should_Respond401_When_ExpiredToken() throws Exception {

         // given
         String token = Jwts.builder()
                .setSubject(MOBILE)
                .setExpiration(new Date(System.currentTimeMillis() - 1000 * 60 * 60 * 10))
                .signWith(SignatureAlgorithm.HS256, properties.getJwtSecret())
                .compact();

        getUser(token)
                .andExpect(status().isUnauthorized());
    }

    @Test
    void getUser_Should_Respond401_When_JwtSignedWithWrongSecret() throws Exception {

        // given
        String token = Jwts.builder()
                .setSubject(MOBILE)
                .signWith(SignatureAlgorithm.HS256, "wrong-secret")
                .compact();

        getUser(token)
                .andExpect(status().isUnauthorized());
    }


    @Test
    @Sql("classpath:itest/user/sql/user.sql")
    void testLoginAndGetUser() throws Exception {

        // given
        var token = getToken(MOBILE, PASSWORD);

        // when
        getUser(token)

                // then
                .andExpect(status().isOk())
                .andExpect(jsonPath("name").value(NAME))
                .andExpect(jsonPath("mobile").value(MOBILE));
    }

    private ResultActions getUser(String token) throws Exception {

        return mockMvc.perform(get("/users/me")
                .header(HttpHeaders.AUTHORIZATION, TOKEN_PREFIX + token)
                .accept(MediaType.APPLICATION_JSON));
    }
}
