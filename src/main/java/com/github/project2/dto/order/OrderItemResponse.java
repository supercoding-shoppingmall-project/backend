package com.github.project2.dto.order;

import com.github.project2.entity.order.OrderItemEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OrderItemResponse {
	private Integer id;
	private String productName;
	private Integer quantity;
	private BigDecimal price;
	private BigDecimal totalPrice;

	// 스태틱 팩토리 메서드
	public static OrderItemResponse from(OrderItemEntity orderItem) {
		return OrderItemResponse.builder()
				.id(orderItem.getId())
				.productName(orderItem.getProduct().getName())
				.quantity(orderItem.getQuantity())
				.price(orderItem.getPrice())
				.totalPrice(orderItem.getTotalPrice())
				.build();
	}
}
