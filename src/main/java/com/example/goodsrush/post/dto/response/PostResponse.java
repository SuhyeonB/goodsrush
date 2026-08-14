package com.example.goodsrush.post.dto.response;

import com.example.goodsrush.post.entity.Post;
import lombok.Builder;

import java.time.LocalDateTime;

@Builder
public record PostResponse(Long id, String title, String content, int likeCount, LocalDateTime createdAt){

    public static PostResponse from (Post post) {
        return PostResponse.builder()
                .id(post.getId())
                .title(post.getTitle())
                .content(post.getContent())
                .likeCount(post.getLikeCount())
                .createdAt(post.getCreatedAt())
                .build();
    }
}
