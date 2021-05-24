package com.joypatel.smalltasks.config;


import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@Slf4j
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Override
    public void configure(HttpSecurity http) throws Exception {

        log.info("Configuring HttpSecurity");
        http
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and()
                .logout().disable()
                .csrf().disable()
                .authorizeRequests().mvcMatchers("/**").permitAll();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {

        log.info("Configuring PasswordEncoder");
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }
}
