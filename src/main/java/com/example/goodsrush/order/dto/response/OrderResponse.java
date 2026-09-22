package com.example.goodsrush.order.dto.response;

import com.example.goodsrush.order.entity.Order;
import com.example.goodsrush.product.entity.Product;
import lombok.Builder;

import java.time.LocalDateTime;

@Builder
public record OrderResponse(Long id, Long productId, String username, int quantity, LocalDateTime createdAt) {
    public static OrderResponse from(Order order) {
        return OrderResponse.builder()
                .id(order.getId())
                .productId(order.getProduct().getId())
                .username(order.getUsername())
                .quantity(order.getQuantity())
                .createdAt(order.getCreatedAt())
                .build();
    }
}
