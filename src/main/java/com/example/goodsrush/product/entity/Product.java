package com.example.goodsrush.product.entity;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor
@Table(name = "products")
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private int stock;

    @Column(nullable = false)
    private long price;

    @Column(updatable = false)
    private LocalDateTime createdAt;

    @PrePersist
    public void onCreate(){
        createdAt = LocalDateTime.now();
    }

    @Builder
    public Product(String name, int stock, long price) {
        if (stock < 0) throw new IllegalArgumentException("재고는 0 이상일 것");
        this.name = name;
        this.stock = stock;
        this.price = price;
    }

    public void purchase(int quantity) {
        if (stock < quantity) {
            throw new IllegalStateException("재고 부족");
        }
        this.stock -= quantity;
    }

    public void update(String name, int stock, long price) {
        this.name = name;
        this.stock = stock;
        this.price = price;
    }
}
