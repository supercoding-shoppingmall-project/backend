package com.github.project2.entity.order.enums;

public enum OrderStatus {
	PENDING,      // 주문이 접수되었으나 아직 처리되지 않은 상태
	PAID,         // 결제가 완료된 상태
	SHIPPED,      // 상품이 배송된 상태
	DELIVERED     // 상품이 고객에게 도착한 상태// 주문이 취소된 상태
}