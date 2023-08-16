package com.app.socialmediaapi.utils;

import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class ResponseOKFactory {
    public static Map<String, String> getTextResponse(RequestObject requestObject){
        switch (requestObject){
            case POST:
                return Map.of("response", "Post is created");
            case USER:
                return Map.of("response", "User is created");
            default:
                throw new RuntimeException("Unknown object");
        }

    }
}
