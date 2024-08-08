package com.github.project2.dto.cart;

import com.github.project2.entity.cart.CartEntity;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Builder
@Data
public class CartResponse {
    private Integer id;
    private Integer userId;
    private BigDecimal totalPrice; // 총가격 필드 추가
    private List<CartItemResponse> items;

    // 스태틱 팩토리 메서드
    public static CartResponse from(CartEntity cart, List<CartItemResponse> items) {
        return CartResponse.builder()
            .id(cart.getId())
            .userId(cart.getUser().getId())
            .totalPrice(cart.getTotalPrice())
            .items(items)
            .build();
    }
}
