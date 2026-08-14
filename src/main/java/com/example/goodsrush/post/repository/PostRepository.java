package com.example.goodsrush.post.repository;

import com.example.goodsrush.post.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostRepository extends JpaRepository<Post, Long> {
}
