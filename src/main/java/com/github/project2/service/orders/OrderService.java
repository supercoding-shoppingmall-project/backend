package com.github.project2.service.orders;

import com.github.project2.dto.order.OrderItemResponse;
import com.github.project2.dto.order.OrderRequest;
import com.github.project2.dto.order.OrderResponse;
import com.github.project2.entity.cart.CartEntity;
import com.github.project2.entity.cart.CartItemEntity;
import com.github.project2.entity.order.OrderItemEntity;
import com.github.project2.entity.order.OrderEntity;
import com.github.project2.entity.order.enums.OrderStatus;
import com.github.project2.entity.post.ProductEntity;
import com.github.project2.entity.post.ProductSizeEntity;
import com.github.project2.repository.cart.CartItemRepository;
import com.github.project2.repository.cart.CartRepository;
import com.github.project2.repository.order.OrderRepository;
import com.github.project2.repository.post.ProductRepository;
import com.github.project2.repository.post.ProductSizeRepository;
import com.github.project2.service.exceptions.NotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
public class OrderService {

	private final OrderRepository orderRepository;
	private final CartRepository cartRepository;
	private final CartItemRepository cartItemRepository;
	private final ProductSizeRepository productSizeRepository;

	/**
	 * 사용자의 장바구니에 있는 물품을 주문으로 생성하는 메서드
	 *
	 * @param orderRequest 사용자의 주문 요청 정보를 포함하는 객체
	 * @return 생성된 주문의 응답 정보를 담은 객체
	 */
	@Transactional
	public OrderResponse createOrderFromCart(OrderRequest orderRequest) {
		// 사용자의 장바구니를 가져옴.
		CartEntity cart = cartRepository.findByUserId(orderRequest.getUserId())
				.orElseThrow(() -> new NotFoundException("Cart not found for user id: " + orderRequest.getUserId()));

		// 장바구니의 총 가격을 계산.
		BigDecimal totalPrice = cart.getCartItems().stream()
				.map(CartItemEntity::getTotalPrice)
				.reduce(BigDecimal.ZERO, BigDecimal::add);

		// 주문 엔티티를 생성.
		OrderEntity order = OrderEntity.create(cart.getUser(), orderRequest.getShippingAddress(), OrderStatus.PENDING, totalPrice);

		List<OrderItemEntity> orderItems = new ArrayList<>();
		for (CartItemEntity cartItem : cart.getCartItems()) {
			ProductEntity product = cartItem.getProduct();

			// ProductSizeEntity를 cartItem의 사이즈 필드를 이용해 가져옴.
			ProductSizeEntity productSize = product.getSizes().stream()
					.filter(size -> size.getSize().equals(cartItem.getSize()))
					.findFirst()
					.orElseThrow(() -> new NotFoundException("Product size not found"));

			// 재고가 부족한 경우 예외를 발생시킵니다.
			if (productSize.getSizeStock() < cartItem.getQuantity()) {
				throw new NotFoundException("Product size out of stock");
			}

			// 주문 항목을 생성하고 리스트에 추가.
			OrderItemEntity orderItem = OrderItemEntity.create(order, product, null, cartItem.getQuantity(), cartItem.getPrice());
			orderItems.add(orderItem);

			// 재고를 차감.
			productSize.setSizeStock(productSize.getSizeStock() - cartItem.getQuantity());
			productSizeRepository.save(productSize);
		}
		// 주문 엔티티에 주문 항목들을 설정.
		order.setOrderItems(orderItems);
		orderRepository.save(order);

		// 장바구니 항목들을 제거.
		List<CartItemEntity> cartItems = cart.getCartItems();
		cart.getCartItems().clear();
		cartItemRepository.deleteAll(cartItems);

		// 장바구니를 삭제.
		cartRepository.delete(cart);

		// 주문 응답 객체를 생성하고, 사용자 이름과 전화번호, 결제 정보를 설정합니다. 결제 방법에 따라 카드 정보 또는 은행 정보를 설정.
		List<OrderItemResponse> orderItemResponses = orderItems.stream()
				.map(OrderItemResponse::from)
				.collect(Collectors.toList());

		OrderResponse orderResponse = OrderResponse.from(order, orderItemResponses);
		orderResponse.setName(orderRequest.getName());
		orderResponse.setPhone(orderRequest.getPhone());
		orderResponse.setPaymentMethod(orderRequest.getPaymentMethod());
		if ("CARD".equals(orderRequest.getPaymentMethod())) {
			orderResponse.setCardNumber(orderRequest.getCardNumber());
			orderResponse.setCardExpiry(orderRequest.getCardExpiry());
			orderResponse.setCardCvv(orderRequest.getCardCvv());
		} else if ("BANK_TRANSFER".equals(orderRequest.getPaymentMethod())) {
			orderResponse.setBankName(orderRequest.getBankName());
			orderResponse.setBankAccount(orderRequest.getBankAccount());
		}

		return orderResponse;
	}

	/**
	 * 주어진 사용자 ID로 주문 목록을 조회하는 메서드
	 *
	 * @param userId 사용자 ID
	 * @return 사용자의 주문 목록을 OrderResponse 객체로 변환하여 반환
	 */
	@Transactional
	public List<OrderResponse> getOrdersByUserId(Integer userId) {
		// 주어진 사용자 ID로 주문 목록을 조회합니다.
		List<OrderEntity> orders = orderRepository.findByUserId(userId);

		// 주문 목록을 OrderResponse로 변환하여 반환.
		return orders.stream()
				.map(order -> {
					// 각 주문의 주문 항목 목록을 OrderItemResponse로 변환.
					List<OrderItemResponse> orderItemResponses = order.getOrderItems().stream()
							.map(OrderItemResponse::from)
							.collect(Collectors.toList());

					// OrderEntity를 OrderResponse로 변환.
					OrderResponse orderResponse = OrderResponse.from(order, orderItemResponses);

					// 추가 정보를 설정하지 않고 기본 정보만 반환.
					return orderResponse;
				})
				.collect(Collectors.toList());
	}
}