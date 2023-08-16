package com.app.socialmediaapi.controller;

import com.app.socialmediaapi.dto.SigninRequest;
import com.app.socialmediaapi.dto.SigninResponse;
import com.app.socialmediaapi.dto.SignupRequest;
import com.app.socialmediaapi.service.UserService;
import com.app.socialmediaapi.utils.Action;
import com.app.socialmediaapi.utils.RequestObject;
import com.app.socialmediaapi.utils.ResponseOKFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class SecurityController {

    private final UserService userService;

    @PostMapping("/signup")
    public ResponseEntity<?> signup(@RequestBody SignupRequest signupRequest) {
        userService.signup(signupRequest);
        return new ResponseEntity<>(ResponseOKFactory.getTextResponse(Action.CREATE, RequestObject.USER), HttpStatus.OK);
    }
    @PostMapping("/signin")
    public ResponseEntity<SigninResponse> signin(@RequestBody SigninRequest signinRequest) {
        return ResponseEntity.ok(userService.signin(signinRequest));
    }


}
