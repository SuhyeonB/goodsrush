package com.example.goodsrush.post.entity;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor
@Table(name = "posts")
public class Post {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String content;

    private int likeCount;

    //@Version
    private int version;

    @Column(updatable = false)
    private LocalDateTime createdAt;

    @PrePersist
    public void onCreate() {
        likeCount = 0;
        createdAt = LocalDateTime.now();
    }

    @Builder
    public Post(String title, String content) {
        this.title = title;
        this.content = content;
    }

    public void increaseLikeCount() {
        likeCount++;
    }

}
