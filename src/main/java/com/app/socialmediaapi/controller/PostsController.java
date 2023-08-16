package com.app.socialmediaapi.controller;

import com.app.socialmediaapi.dto.PostDto;
import com.app.socialmediaapi.model.Post;
import com.app.socialmediaapi.service.PostService;
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
@RequestMapping("/api/posts")
@RequiredArgsConstructor
public class PostsController {
    private final PostService postService;

    @PostMapping("/save")
    public ResponseEntity<?> save(@RequestHeader(HttpHeaders.AUTHORIZATION) String token, @ModelAttribute PostDto postDto) {
        postService.save(token, postDto);
        return new ResponseEntity<>(ResponseOKFactory.getTextResponse(Action.CREATE, RequestObject.POST), HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<List<Post>> getPostsByUserName(@RequestParam("username") String username){
        return ResponseEntity.ok(postService.getPostsByUserName(username));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deletePostById(@RequestHeader(HttpHeaders.AUTHORIZATION) String token, @PathVariable Long id){
        postService.deletePostById(token, id);
        return new ResponseEntity<>(ResponseOKFactory.getTextResponse(Action.DELETE, RequestObject.POST), HttpStatus.OK);
    }
}
