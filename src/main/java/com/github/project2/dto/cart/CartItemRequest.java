package com.github.project2.dto.cart;

import lombok.*;

@Builder
@Data
public class CartItemRequest {
    private Integer productId;
    private Integer size;
    private Integer quantity;

    // 스태틱 팩토리 메서드
    public static CartItemRequest of(Integer productId, Integer size, Integer quantity) {
        return CartItemRequest.builder()
            .productId(productId)
            .size(size)
            .quantity(quantity)
            .build();
    }
}