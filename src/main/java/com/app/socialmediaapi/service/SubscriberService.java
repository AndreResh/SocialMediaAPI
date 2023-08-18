package com.app.socialmediaapi.service;

import com.app.socialmediaapi.model.Friend;
import com.app.socialmediaapi.model.Post;
import com.app.socialmediaapi.model.Subscriber;
import com.app.socialmediaapi.model.User;
import com.app.socialmediaapi.repository.FriendRepository;
import com.app.socialmediaapi.repository.PostRepository;
import com.app.socialmediaapi.repository.SubscribeRepository;
import com.app.socialmediaapi.utils.JwtHelper;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class SubscriberService {

    private final JwtHelper jwtHelper;
    private final SubscribeRepository subscribeRepository;
    private final FriendRepository friendRepository;
    private final PostRepository postRepository;

    public void subscribe(String token, Long id) {
        User user = jwtHelper.findUserByToken(token);
        if (Objects.equals(id, user.getId()))
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "You can't be friend for yourself, he-he-he");
        boolean isMeAlreadySubscribe = subscribeRepository.findAllByMe(user.getId())
                .stream().anyMatch(subscriber -> subscriber.getAnotherPerson().equals(id));
        if (isMeAlreadySubscribe)
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "You have already subscribe on this person");
        subscribeRepository.save(new Subscriber(user.getId(), id));
        boolean isAnotherPersonAlreadySubscribe = subscribeRepository.findAllByMe(id)
                .stream().anyMatch(subscriber -> subscriber.getAnotherPerson().equals(user.getId()));
        if (isAnotherPersonAlreadySubscribe) {
            Optional<Friend> optionalFriend = friendRepository.findByFirstFriendAndSecondFriend(user.getId(), id);
            if (optionalFriend.isPresent()) {
                Friend friend = optionalFriend.get();
                if (friend.isFriends())
                    throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "You are already friends");
                friend.setFriends(true);
                friendRepository.save(friend);
            } else {
                friendRepository.save(new Friend(user.getId(), id));
            }
        }
    }

    public void unsubscribe(String token, Long id) {
        User user = jwtHelper.findUserByToken(token);
        if (Objects.equals(id, user.getId()))
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "You can't unsubscribe from yourself");
        boolean isMeAlreadySubscribe = subscribeRepository.findAllByMe(user.getId())
                .stream().anyMatch(subscriber -> subscriber.getAnotherPerson().equals(id));
        if (!isMeAlreadySubscribe)
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "You don't subscribe for this person");
        subscribeRepository.deleteByMeAndAnotherPerson(user.getId(), id);
    }


    public List<Post> getMyTape(String token, Integer limit) {
        User user = jwtHelper.findUserByToken(token);
        List<Long> subscribersIds = subscribeRepository
                .findAllByMe(user.getId())
                .stream().map(Subscriber::getAnotherPerson)
                .collect(Collectors.toList());
        Pageable pageable = PageRequest.of(0, limit, Sort.by("create_time").descending());
        return postRepository.findBySubscriberIds(subscribersIds, pageable).getContent();
    }
}
