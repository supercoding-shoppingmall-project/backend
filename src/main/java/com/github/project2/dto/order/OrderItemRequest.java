package com.github.project2.dto.order;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OrderItemRequest {
	private Integer productId;
	private Integer quantity;
	private BigDecimal price;

	// 스태틱 팩토리 메서드
	public static OrderItemRequest of(Integer productId, Integer quantity, BigDecimal price) {
		return OrderItemRequest.builder()
				.productId(productId)
				.quantity(quantity)
				.price(price)
				.build();
	}
}
