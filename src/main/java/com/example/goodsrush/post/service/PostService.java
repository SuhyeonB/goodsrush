package com.example.goodsrush.post.service;

import com.example.goodsrush.post.dto.request.CreatePostRequest;
import com.example.goodsrush.post.dto.request.UpdatePostRequest;
import com.example.goodsrush.post.dto.response.PostResponse;
import com.example.goodsrush.post.entity.Post;
import com.example.goodsrush.post.repository.PostRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class PostService {

    private final PostRepository postRepository;

    @Transactional
    public PostResponse createPost(CreatePostRequest dto) {
        Post post = Post.builder()
                .title(dto.getTitle())
                .content(dto.getContent())
                .build();

        postRepository.save(post);

        return PostResponse.from(post);
    }

    @Transactional(readOnly = true)
    public Page<PostResponse> getAllPosts(Pageable pageable) {
        return postRepository.findAll(pageable)
                .map(PostResponse::from);
    }

    @Transactional(readOnly = true)
    public PostResponse getPost(Long id) {
        return postRepository.findById(id)
                .map(PostResponse::from)
                .orElseThrow(() -> new EntityNotFoundException("Post not found: " + id));
    }

    @Transactional
    public PostResponse updatePost(Long id, UpdatePostRequest dto) {
        Post post = postRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Post not found: " + id));

        post.update(dto.getTitle(), dto.getContent());

        return PostResponse.from(post);
    }

    @Transactional
    public void deletePost(Long id) {
        if (!postRepository.existsById(id)) {
            throw new EntityNotFoundException("Post not found: " + id);
        }
        postRepository.deleteById(id);
    }

    @Transactional
    public void likePost(Long id) {
        Post post = postRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Post not found: " + id));

        post.increaseLikeCount();
    }
}
