package com.example.goodsrush.post.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class CreatePostRequest {
    @NotBlank
    private String title;
    @NotBlank
    private String content;
}
