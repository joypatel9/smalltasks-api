package com.joypatel.smalltasks.common;

import com.joypatel.smalltasks.config.AuthTokenFilter;
import com.joypatel.smalltasks.config.MyProperties;
import io.jsonwebtoken.Claims;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class JwtServiceTest {

    @Mock
    private MyProperties properties;

    @InjectMocks
    private JwtService service;

    @Test
    void testJwt() {

        // given
        String user = "user@example.com";
        String userIdKey = "userId";
        Integer userId = 35;
        String authoritiesKey = "authorities";
        List<SimpleGrantedAuthority> authorities = List.of(new SimpleGrantedAuthority("USER"));

        when(properties.getJwtSecret()).thenReturn("top-secret");

        // when
        String token = service.createToken(user, Map.of(userIdKey, userId, authoritiesKey, authorities));

        // then
        Claims claims = service.extractClaims(token);
        assertFalse(service.isTokenExpired(claims));
        assertEquals(user, claims.getSubject());
        assertEquals(userId, claims.get(userIdKey, Integer.class));
        assertEquals(authorities, AuthTokenFilter.toAuthorities(claims.get("authorities", ArrayList.class)));
    }
}
