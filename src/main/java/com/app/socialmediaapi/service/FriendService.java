package com.app.socialmediaapi.service;

import com.app.socialmediaapi.model.Friend;
import com.app.socialmediaapi.model.User;
import com.app.socialmediaapi.repository.FriendRepository;
import com.app.socialmediaapi.repository.SubscribeRepository;
import com.app.socialmediaapi.utils.JwtHelper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
@RequiredArgsConstructor
public class FriendService {

    private final JwtHelper jwtHelper;
    private final SubscribeRepository subscribeRepository;
    private final FriendRepository friendRepository;

    public void deleteFriend(String token, Long id) {
        User user = jwtHelper.findUserByToken(token);
        Friend friend = getFriendByFirstFriendAndSecondFriend(user.getId(), id);
        friend.setFriends(false);
        subscribeRepository.deleteByMeAndAnotherPerson(user.getId(), id);
        friendRepository.save(friend);
    }

    public void sendMessage(String token, Long id, String message) {
        User user = jwtHelper.findUserByToken(token);
        Friend friend = getFriendByFirstFriendAndSecondFriend(user.getId(), id);
        friend.getMessages().add(message);
        friendRepository.save(friend);
    }

    private Friend getFriendByFirstFriendAndSecondFriend(Long id1, Long id2){
        return friendRepository.findByFirstFriendAndSecondFriend(id1, id2)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "You are not friends"));
    }

    public List<String> getMessages(String token, Long id) {
        User user = jwtHelper.findUserByToken(token);
        Friend friend = getFriendByFirstFriendAndSecondFriend(user.getId(), id);
        return friend.getMessages();
    }
}
