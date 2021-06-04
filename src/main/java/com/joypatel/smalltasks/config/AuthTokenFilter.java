package com.joypatel.smalltasks.config;

import com.joypatel.smalltasks.common.JwtService;
import com.joypatel.smalltasks.common.security.MyUserDetails;
import io.jsonwebtoken.Claims;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Filter for token authentication
 */


@Component
@AllArgsConstructor
@Slf4j
public class AuthTokenFilter extends OncePerRequestFilter {

    private static final String TOKEN_PREFIX = "Bearer ";
    private static final int TOKEN_PREFIX_LENGTH = 7;

    private final JwtService jwtService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        log.info("Inside AuthTokenFilter ...");

        String header = request.getHeader(HttpHeaders.AUTHORIZATION);
        if (header != null && header.startsWith(TOKEN_PREFIX)) { // token present

            log.info("Found a token");
            String token = header.substring(TOKEN_PREFIX_LENGTH);

            try {

                Authentication auth = createAuthToken(token);
                SecurityContextHolder.getContext().setAuthentication(auth);

                log.info("Token authentication successful");

            } catch (Exception e) {

                log.info("Token authentication failed - {}", e.getMessage());

                response.sendError(HttpServletResponse.SC_UNAUTHORIZED,
                        "Authentication Failed: " + e.getMessage());

                return;
            }

        } else

            log.debug("Token authentication skipped");

        filterChain.doFilter(request, response);
    }

    protected Authentication createAuthToken(String token) {

        Claims claims = jwtService.extractClaims(token);
        if (jwtService.isTokenExpired(claims))
            throw new RuntimeException("Auth token expired");

        MyUserDetails userDetails = MyUserDetails.builder()
                .username(claims.getSubject())
                .id(claims.get("userId", Integer.class))
                .authorities(toAuthorities(claims.get("authorities", ArrayList.class)))
                .build();

        return new UsernamePasswordAuthenticationToken(userDetails, token, userDetails.getAuthorities());
    }

    public static Collection<? extends GrantedAuthority> toAuthorities(List<Map<String, String>> authorities) {
        return authorities.stream()
                .map(authority -> authority.get("authority"))
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toList());
    }
}
