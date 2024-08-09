package com.github.project2.dto.order;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Builder
@Data
public class OrderRequest {
	private Integer userId;
	private String name;
	private String phone;
	private String shippingAddress;
	private String paymentMethod; // "CARD" 또는 "BANK_TRANSFER"
	private String cardNumber; // 카드 번호
	private String cardExpiry; // 카드 만료일
	private String cardCvv; // 카드 CVV 코드
	private String bankName; // 은행 이름
	private String bankAccount; // 계좌 번호
	private List<OrderItemRequest> items;

	// 스태틱 팩토리 메서드
	public static OrderRequest of(Integer userId, String name, String phone, String shippingAddress, String paymentMethod, String cardNumber, String cardExpiry, String cardCvv, String bankName, String bankAccount) {
		return OrderRequest.builder()
				.userId(userId)
				.name(name)
				.phone(phone)
				.shippingAddress(shippingAddress)
				.paymentMethod(paymentMethod)
				.cardNumber(cardNumber)
				.cardExpiry(cardExpiry)
				.cardCvv(cardCvv)
				.bankName(bankName)
				.bankAccount(bankAccount)
				.build();
	}
}