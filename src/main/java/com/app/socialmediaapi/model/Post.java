package com.app.socialmediaapi.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "posts")
public class Post {
    public Post(String header, String text, byte[] content, User user) {
        this.header = header;
        this.text = text;
        this.content = content;
        this.user = user;
        this.createTime = LocalDateTime.now();
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String header;
    private String text;
    @Lob
    @Type(type = "org.hibernate.type.ImageType")
    private byte[] content;
    private LocalDateTime createTime;
    @ManyToOne
    @JoinColumn(name = "user_id")
    @JsonIgnore
    private User user;
}
