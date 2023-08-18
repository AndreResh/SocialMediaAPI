package com.app.socialmediaapi.repository;

import com.app.socialmediaapi.model.Subscriber;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;

@Repository
public interface SubscribeRepository extends JpaRepository<Subscriber, Long> {
    List<Subscriber> findAllByMe(Long me);

    List<Subscriber> findAllByAnotherPerson(Long anotherPerson);
    @Transactional
    void deleteByMeAndAnotherPerson(Long me, Long anotherPerson);
}
