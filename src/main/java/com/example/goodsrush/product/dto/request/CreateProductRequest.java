package com.example.goodsrush.product.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class CreateProductRequest {

    @NotBlank
    private String name;

    @NotNull
    private int stock;

    @NotNull
    private long price;
}
