package com.app.socialmediaapi.utils;

import com.app.socialmediaapi.config.AppSecret;
import com.app.socialmediaapi.model.User;
import com.app.socialmediaapi.repository.UserRepository;
import io.jsonwebtoken.Jwts;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;

@Component
@RequiredArgsConstructor
public class JwtHelper {
    private final AppSecret appSecret;
    private final UserRepository userRepository;
    public User findUserByToken(String token){
        String[] payload = getPayloadFromJwt(token.substring(7)).split(" ");
        return userRepository.findUserByUsernameAndEmail(payload[0], payload[1]).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "User with this email and username doesnt exists")
        );
    }

    public String getPayloadFromJwt(String token){
        return Jwts.parser().setSigningKey(appSecret.getKey()).parseClaimsJws(token).getBody().getSubject();
    }
}
