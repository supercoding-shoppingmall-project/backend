package com.github.project2.dto.cart;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Builder
@Data
public class CartRequest {
    private Integer userId;
    private List<CartItemRequest> items;

    // 스태틱 팩토리 메서드
    public static CartRequest of(Integer userId, List<CartItemRequest> items) {
        return CartRequest.builder()
            .userId(userId)
            .items(items)
            .build();
    }
}
