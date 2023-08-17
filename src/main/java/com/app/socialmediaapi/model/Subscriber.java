package com.app.socialmediaapi.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "subscribers")
public class Subscriber {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long me;

    private Long anotherPerson;

    public Subscriber(Long me, Long anotherPerson) {
        this.me = me;
        this.anotherPerson = anotherPerson;
    }
}
