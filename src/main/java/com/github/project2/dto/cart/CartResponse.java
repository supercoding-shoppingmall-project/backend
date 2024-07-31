package com.github.project2.dto.cart;

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
}
