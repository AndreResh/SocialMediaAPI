package com.app.socialmediaapi.controller;

import com.app.socialmediaapi.service.FriendService;
import com.app.socialmediaapi.utils.Action;
import com.app.socialmediaapi.utils.RequestObject;
import com.app.socialmediaapi.utils.ResponseOKFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/friend")
@RequiredArgsConstructor
public class FriendController {

    private final FriendService friendService;

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteFriend(@RequestHeader(HttpHeaders.AUTHORIZATION) String token, @PathVariable Long id) {
        friendService.deleteFriend(token, id);
        return new ResponseEntity<>(ResponseOKFactory.getTextResponse(Action.DELETE, RequestObject.FRIEND), HttpStatus.OK);
    }

    @PostMapping("/message/{id}")
    public ResponseEntity<?> sendMessage(@RequestHeader(HttpHeaders.AUTHORIZATION) String token, @PathVariable Long id, @RequestParam String message) {
        friendService.sendMessage(token, id, message);
        return new ResponseEntity<>(ResponseOKFactory.getTextResponse(Action.CREATE, RequestObject.MESSAGE), HttpStatus.OK);
    }

    @GetMapping("/message/{id}")
    public ResponseEntity<?> getMessages(@RequestHeader(HttpHeaders.AUTHORIZATION) String token, @PathVariable Long id) {
        return new ResponseEntity<>(friendService.getMessages(token, id), HttpStatus.OK);
    }
}
