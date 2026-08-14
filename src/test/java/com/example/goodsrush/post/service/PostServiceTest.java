package com.example.goodsrush.post.service;

import com.example.goodsrush.post.entity.Post;
import com.example.goodsrush.post.repository.PostRepository;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class PostServiceTest {

    @Autowired
    PostRepository postRepository;

    @Autowired
    PostService postService;

    @Test
    void likes_no_retry() throws InterruptedException {
        // given
        Post post = postRepository.save(Post.builder().title("test").content("contents").build());

        int threadCount = 100;
        ExecutorService executorService = Executors.newFixedThreadPool(32);
        CountDownLatch latch = new CountDownLatch(threadCount);

        // when
        for (int i=0; i<threadCount; i++) {
            executorService.submit(() -> {
                try {
                    postService.likePost(post.getId());
                } catch (Exception e) {
                    // Ignore exception and continue (expected in Before state)
                } finally {
                    latch.countDown();
                }
            });
        }
        latch.await();

        // then
        Post result = postRepository.findById(post.getId()).orElseThrow();
        System.out.println("final likeCount: " + result.getLikeCount());    // maybe less than 100
    }
}