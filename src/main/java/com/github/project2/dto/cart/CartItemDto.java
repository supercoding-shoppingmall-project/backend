package com.github.project2.dto.cart;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CartItemDto {
    private Integer id;
    private String productName;
    private String category;
    private String size;
    private Integer quantity;
    private BigDecimal price;
    private String shippingAddress;
}
