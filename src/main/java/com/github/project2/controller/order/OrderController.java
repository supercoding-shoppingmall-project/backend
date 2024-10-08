package com.github.project2.controller.order;

import com.github.project2.dto.order.OrderRequest;
import com.github.project2.dto.order.OrderResponse;
import com.github.project2.service.orders.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/orders")
@RequiredArgsConstructor
public class OrderController {

	private final OrderService orderService;

	/**
	 * 장바구니의 물품을 주문으로 생성하는 엔드포인트.
	 *
	 * @param orderRequest 주문 요청 정보를 포함하는 객체.
	 * @return 생성된 주문 정보를 포함하는 응답 객체.
	 */
	@PostMapping("/create-from-cart")
	public ResponseEntity<OrderResponse> createOrderFromCart(@RequestBody OrderRequest orderRequest) {
		// OrderService를 호출하여 주문을 생성.
		OrderResponse orderResponse = orderService.createOrderFromCart(orderRequest);
		// 생성된 주문 정보를 포함하는 응답을 반환.
		return new ResponseEntity<>(orderResponse, HttpStatus.CREATED);
	}

	/**
	 * 사용자 ID로 주문 목록을 조회하는 엔드포인트.
	 *
	 * @param userId 주문 목록을 조회할 사용자 ID.
	 * @return 사용자의 주문 목록을 포함하는 응답 객체.
	 */
	@GetMapping("/user/{userId}")
	public ResponseEntity<List<OrderResponse>> getOrdersByUserId(@PathVariable Integer userId) {
		// OrderService를 호출하여 사용자의 주문 목록을 조회.
		List<OrderResponse> orderResponses = orderService.getOrdersByUserId(userId);
		// 조회된 주문 목록을 포함하는 응답을 반환.
		return new ResponseEntity<>(orderResponses, HttpStatus.OK);
	}
}
