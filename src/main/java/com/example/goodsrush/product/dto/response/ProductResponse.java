package com.example.goodsrush.product.dto.response;

import com.example.goodsrush.product.entity.Product;
import lombok.Builder;

import java.time.LocalDateTime;

@Builder
public record ProductResponse(Long id, String name, int stock, long price, LocalDateTime createdAt) {

    public static ProductResponse from (Product product) {
        return ProductResponse.builder()
                .id(product.getId())
                .name(product.getName())
                .stock(product.getStock())
                .price(product.getPrice())
                .createdAt(product.getCreatedAt())
                .build();
    }
}
