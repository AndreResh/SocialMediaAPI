package com.app.socialmediaapi.repository;

import com.app.socialmediaapi.model.Friend;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Repository
public interface FriendRepository extends JpaRepository<Friend, Long> {
    @Query("SELECT f FROM Friend f where (f.firstFriend = ?1 and  f.secondFriend = ?2) OR (f.firstFriend = ?2 and  f.secondFriend = ?1)")
    Optional<Friend> findByFirstFriendAndSecondFriend(Long firstFriend, Long secondFriend);
}
