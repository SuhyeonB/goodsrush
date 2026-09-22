package com.example.goodsrush.order.controller;

import com.example.goodsrush.order.dto.request.CreateOrderRequest;
import com.example.goodsrush.order.dto.response.OrderResponse;
import com.example.goodsrush.order.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/orders")
public class OrderController {

    private final OrderService orderService;

    @PostMapping("/products/{productId}/purchase")
    public ResponseEntity<OrderResponse> purchase (
            @PathVariable Long productId,
            @RequestBody CreateOrderRequest dto
    ) {
        return ResponseEntity.status(HttpStatus.CREATED).body(orderService.purchase(productId, dto));
    }

    @GetMapping("/{id}")
    public ResponseEntity<OrderResponse> getOrder(@PathVariable Long id) {
        return ResponseEntity.ok(orderService.getOrder(id));
    }

    @GetMapping
    public ResponseEntity<Page<OrderResponse>> getAllOrders(
            @PageableDefault(size = 10, sort = "createdAt", direction = Sort.Direction.DESC) Pageable pageable
    ) {
        return ResponseEntity.ok(orderService.getAllOrders(pageable));
    }
}
