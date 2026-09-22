package com.example.goodsrush.product.dto.request;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class UpdateProductRequest {

    private String name;
    private int stock;
    private long price;
}
