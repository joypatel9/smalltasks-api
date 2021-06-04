package com.joypatel.smalltasks.common;

import com.joypatel.smalltasks.config.MyProperties;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.Map;

@Component
@AllArgsConstructor
@Slf4j
public class JwtService {

    private final MyProperties properties;

    public Claims extractClaims(String token) {
        return Jwts.parser()
                .setSigningKey(properties.getJwtSecret())
                .parseClaimsJws(token)
                .getBody();
    }

    public Boolean isTokenExpired(Claims claims) {
        Boolean test = claims.getExpiration().before(new Date());
        return test;
    }

    public String createToken(String subject, Map<String, Object> claims) {

        log.info("JWT Secret {}", properties.getJwtSecret());

        return Jwts.builder()
                .setSubject(subject)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 10))
                .addClaims(claims)
                .signWith(SignatureAlgorithm.HS256, properties.getJwtSecret())
                .compact();
    }
}
