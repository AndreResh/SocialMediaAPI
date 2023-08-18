package com.app.socialmediaapi.controller;

import com.app.socialmediaapi.model.Post;
import com.app.socialmediaapi.service.SubscriberService;
import com.app.socialmediaapi.utils.Action;
import com.app.socialmediaapi.utils.RequestObject;
import com.app.socialmediaapi.utils.ResponseOKFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class SubscriberController {

    private final SubscriberService subscriberService;
    @PostMapping("/subscribe/{id}")
    public ResponseEntity<?> subscribe(@RequestHeader(HttpHeaders.AUTHORIZATION) String token, @PathVariable Long id) {
        subscriberService.subscribe(token, id);
        return new ResponseEntity<>(ResponseOKFactory.getTextResponse(Action.CREATE, RequestObject.SUBSCRIBE), HttpStatus.OK);
    }

    @PostMapping("/unsubscribe/{id}")
    public ResponseEntity<?> unsubscribe(@RequestHeader(HttpHeaders.AUTHORIZATION) String token, @PathVariable Long id) {
        subscriberService.unsubscribe(token, id);
        return new ResponseEntity<>(ResponseOKFactory.getTextResponse(Action.DELETE, RequestObject.SUBSCRIBE), HttpStatus.OK);
    }

    @GetMapping("/tape")
    public ResponseEntity<List<Post>> getMyTape(@RequestHeader(HttpHeaders.AUTHORIZATION) String token,
                                                @RequestParam(value = "limit",
                                                        defaultValue = "5",
                                                        required = false) Integer limit){
        return ResponseEntity.ok(subscriberService.getMyTape(token, limit));
    }
}
