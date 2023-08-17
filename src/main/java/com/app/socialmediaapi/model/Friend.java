package com.app.socialmediaapi.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "friends")
public class Friend {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long firstFriend;
    private Long secondFriend;
    @ElementCollection
    private List<String> messages;
    private boolean isFriends = true;

    public Friend(Long firstFriend, Long secondFriend) {
        this.firstFriend = firstFriend;
        this.secondFriend = secondFriend;
    }
}
