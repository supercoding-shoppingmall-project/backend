package com.github.project2.dto.cart;

import com.github.project2.entity.cart.CartItemEntity;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;

@Builder
@Data
public class CartItemResponse {
    private Integer id;
    private Integer userId;
    private Integer productId;
    private String name;
    private Integer quantity;
    private Integer size;
    private BigDecimal price;
    private BigDecimal totalPrice;
    private String productImageUrl;
     // 유저 ID 추가

    // 스태틱 팩토리 메서드
    public static CartItemResponse from(CartItemEntity cartItem, String productImageUrl) {
        return CartItemResponse.builder()
            .id(cartItem.getId())
            .userId(cartItem.getCart().getUser().getId()) // 유저 ID 설정
            .productId(cartItem.getProduct().getId())
                .name(cartItem.getProduct().getName())
            .size(cartItem.getSize())
            .quantity(cartItem.getQuantity())
            .price(cartItem.getPrice())
            .totalPrice(cartItem.getTotalPrice())
            .productImageUrl(productImageUrl)
            .build();
    }
}
