package com.example.goodsrush.order.service;

import com.example.goodsrush.order.dto.request.CreateOrderRequest;
import com.example.goodsrush.order.dto.response.OrderResponse;
import com.example.goodsrush.order.entity.Order;
import com.example.goodsrush.order.repository.OrderRepository;
import com.example.goodsrush.product.entity.Product;
import com.example.goodsrush.product.repository.ProductRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;
    private final ProductRepository productRepository;

    @Transactional
    public OrderResponse purchase(Long productId, CreateOrderRequest dto) {
        Product product = productRepository.findByIdForUpdate(productId)
                .orElseThrow(() -> new EntityNotFoundException("Product not found: " + productId));

        product.purchase(dto.getQuantity());

        Order order = Order.builder()
                .product(product)
                .username(dto.getUsername())
                .quantity(dto.getQuantity())
                .build();

        orderRepository.save(order);
        return OrderResponse.from(order);
    }

    // 락 없이 조회 (동시성 문제 재현용)
    @Transactional
    public OrderResponse purchaseWithoutLock(Long productId, CreateOrderRequest dto) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new EntityNotFoundException("Product not found: " + productId));

        product.purchase(dto.getQuantity());

        Order order = Order.builder()
                .product(product)
                .username(dto.getUsername())
                .quantity(dto.getQuantity())
                .build();

        orderRepository.save(order);
        return OrderResponse.from(order);
    }

    @Transactional(readOnly = true)
    public OrderResponse getOrder(Long id) {
        return orderRepository.findById(id)
                .map(OrderResponse::from)
                .orElseThrow(() -> new EntityNotFoundException("Order not found: " + id));
    }

    @Transactional(readOnly = true)
    public Page<OrderResponse> getAllOrders(Pageable pageable) {
        return orderRepository.findAll(pageable)
                .map(OrderResponse::from);
    }
}
