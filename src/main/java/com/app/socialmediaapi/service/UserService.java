package com.app.socialmediaapi.service;

import com.app.socialmediaapi.dto.SigninRequest;
import com.app.socialmediaapi.dto.SigninResponse;
import com.app.socialmediaapi.dto.SignupRequest;
import com.app.socialmediaapi.model.User;
import com.app.socialmediaapi.repository.UserRepository;
import com.app.socialmediaapi.security.JwtConfig;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final AuthenticationManager authenticationManager;
    private final JwtConfig jwtConfig;
    private final PasswordEncoder encoder;

    public void signup(SignupRequest signupRequest) {
        if (userRepository.existsByEmail(signupRequest.getEmail()) || userRepository.existsByUsername(signupRequest.getUsername())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "User with this email or username exists");
        }
        User user = new User();
        user.setUsername(signupRequest.getUsername());
        user.setPassword(encoder.encode(signupRequest.getPassword()));
        user.setEmail(signupRequest.getEmail());
        userRepository.save(user);
    }

    public SigninResponse signin(SigninRequest signinRequest) {
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(signinRequest.getUsername(), signinRequest.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtConfig.generateToken(authentication);
        return new SigninResponse(jwt);
    }


}
