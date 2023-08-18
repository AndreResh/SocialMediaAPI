package com.app.socialmediaapi.repository;

import com.app.socialmediaapi.model.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {
    @Query(value = "select * from posts p where p.user_id in :ids", nativeQuery = true)
    Page<Post> findBySubscriberIds(@Param("ids") List<Long> ids, Pageable pageable);
}
