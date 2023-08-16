package com.app.socialmediaapi.utils;

import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class ResponseOKFactory {
    public static Map<String, String> getTextResponse(Action action, RequestObject requestObject) {
        String result;
        switch (action) {
            case CREATE:
                result = "created";
                break;
            case DELETE:
                result = "deleted";
                break;
            default:
                throw new RuntimeException("Unknown action");
        }
        switch (requestObject) {
            case POST:
                return Map.of("response", "Post is " + result);
            case USER:
                return Map.of("response", "User is " + result);
            default:
                throw new RuntimeException("Unknown object");
        }
    }
}
