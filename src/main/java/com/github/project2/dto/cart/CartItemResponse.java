package com.github.project2.dto.cart;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;

@Builder
@Data
public class CartItemResponse {
    private Integer id;
    private Integer productId;
    private Integer quantity;
    private BigDecimal price;
    private BigDecimal totalPrice;
}
