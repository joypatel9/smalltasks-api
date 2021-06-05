package com.joypatel.smalltasks.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.joypatel.smalltasks.common.JwtService;
import com.joypatel.smalltasks.common.MyUtils;
import com.joypatel.smalltasks.common.security.MyUserDetails;
import com.joypatel.smalltasks.user.dtos.AuthToken;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;

@Component
@AllArgsConstructor
@Slf4j
public class MyAuthenticationSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

    private final ObjectMapper objectMapper;
    private final JwtService jwtService;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request,
                                        HttpServletResponse response,
                                        Authentication authentication)
            throws IOException {

        // Instead of handle(request, response, authentication),
        // the statements below are introduced
        response.setStatus(HttpServletResponse.SC_OK);
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);

        MyUserDetails userDetails = MyUtils.getCurrentUser(authentication);
        String token = jwtService.createToken(userDetails.getUsername(),
                Map.of("userId", userDetails.getId(), "authorities", userDetails.getAuthorities()));

        // write current-user data to the response
        response.getOutputStream().print(
                objectMapper.writeValueAsString(AuthToken.builder()
                        .token(token)
                        .build()));

        // as done in the base class
        clearAuthenticationAttributes(request);
        log.info("Authentication succeeded for {}", userDetails);
    }

}
