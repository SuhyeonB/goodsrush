package com.example.goodsrush.order.repository;

import com.example.goodsrush.order.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order, Long> {
}
