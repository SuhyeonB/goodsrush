package com.example.goodsrush.order.dto.request;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class CreateOrderRequest {

    private String username;
    private int quantity;

    public CreateOrderRequest(String username, int quantity) {
        this.username = username;
        this.quantity = quantity;
    }
}
