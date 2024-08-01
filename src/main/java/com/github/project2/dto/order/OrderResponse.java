package com.github.project2.dto.order;

import com.github.project2.entity.order.OrderEntity;
import com.github.project2.entity.order.enums.OrderStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OrderResponse {
	private Integer id;
	private String shippingAddress;
	private BigDecimal totalPrice;
	private OrderStatus status;
	private LocalDateTime createdAt;
	private List<OrderItemResponse> orderItems;
	private String name; // 사용자 이름 추가
	private String phone; // 사용자 전화번호 추가

	// 결제 관련 필드 추가
	private String paymentMethod;
	private String cardNumber;
	private String cardExpiry;
	private String cardCvv;
	private String bankName;
	private String bankAccount;

	public static OrderResponse from(OrderEntity order, List<OrderItemResponse> orderItems) {
		return OrderResponse.builder()
				.id(order.getId())
				.shippingAddress(order.getShippingAddress())
				.totalPrice(order.getTotalPrice())
				.status(order.getStatus())
				.createdAt(order.getCreatedAt())
				.orderItems(orderItems)
				.build();
	}
}