package com.app.socialmediaapi.security;

import com.app.socialmediaapi.config.AppSecret;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;

@Component
@RequiredArgsConstructor
public class JwtConfig {

    private final AppSecret appSecret;

    public String generateToken(Authentication authentication) {
        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        return Jwts.builder()
                .setSubject(String.join(" ", List.of(userDetails.getUsername(), userDetails.getEmail())))
                .setIssuedAt(new Date())
                .setExpiration(new Date((new Date()).getTime() + appSecret.getLifetime()))
                .signWith(SignatureAlgorithm.HS256, appSecret.getKey())
                .compact();
    }
}
