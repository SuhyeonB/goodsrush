package com.example.goodsrush.order.entity;

import com.example.goodsrush.product.entity.Product;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor
@Table(name = "orders")
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id", nullable = false)
    private Product product;

    @Column(nullable = false)
    private String username;

    @Column(nullable = false)
    private int quantity;

    @Column(updatable = false)
    private LocalDateTime createdAt;

    @PrePersist
    public void onCreate() {
        createdAt = LocalDateTime.now();
    }

    @Builder
    public Order(Product product, String username, int quantity) {
        if (quantity <= 0) throw new IllegalArgumentException("수량은 1개 이상이어야 합니다.");
        this.product = product;
        this.username = username;
        this.quantity = quantity;
    }
}
