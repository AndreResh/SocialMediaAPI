package com.app.socialmediaapi.service;

import com.app.socialmediaapi.dto.PostDto;
import com.app.socialmediaapi.model.Post;
import com.app.socialmediaapi.model.User;
import com.app.socialmediaapi.repository.PostRepository;
import com.app.socialmediaapi.repository.UserRepository;
import com.app.socialmediaapi.security.JwtConfig;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.io.IOException;
import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class PostService {

    private final JwtConfig jwtConfig;
    private final UserRepository userRepository;
    private final PostRepository postRepository;

    public void save(String token, PostDto postDto) {
        User user = findUserByToken(token);
        Post post;
        try {
            post = new Post(postDto.getHeader(), postDto.getText(), postDto.getImage().getBytes(), user);
        } catch (IOException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Bad photo");
        }
        postRepository.save(post);
    }

    public List<Post> getPostsByUserName(String username) {
        User user = userRepository.findUserByUsername(username).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "User with this username doesn't exists")
        );
        return user.getPosts();
    }

    public void deletePostById(String token, Long id) {
        User user = findUserByToken(token);
        Post post = postRepository.findById(id).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "Post with this id doesn't exist")
        );
        if(!Objects.equals(post.getUser().getId(), user.getId())){
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "You can't delete post of another user");
        }
        postRepository.deleteById(id);
    }

    private User findUserByToken(String token){
        String[] payload = jwtConfig.getPayloadFromJwt(token.substring(7)).split(" ");
        return userRepository.findUserByUsernameAndEmail(payload[0], payload[1]).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "User with this email and username doesnt exists")
        );
    }
}
