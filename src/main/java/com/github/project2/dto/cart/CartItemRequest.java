package com.github.project2.dto.cart;

import lombok.*;

import java.util.List;

@Builder
@Data
public class CartItemRequest {
    private Integer productId;
    private Integer size; // 사이즈 정보를 추가합니다.
    private Integer quantity;

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class OrderRequestDto {
        private String shippingAddress;
        private List<OrderItemDto> items;

    }
}
