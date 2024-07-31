package com.github.project2.dto.cart;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Builder
@Data
public class CartRequest {
    private Integer userId;
    private List<CartItemRequest> items;
}