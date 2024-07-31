package com.github.project2.entity.order;

import com.github.project2.entity.order.enums.OrderStatus;
import com.github.project2.entity.user.UserEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "Orders")
public class OrderEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	@ManyToOne
	@JoinColumn(name = "user_id", nullable = false)
	private UserEntity user;

	@Column(name = "shipping_address", nullable = false)
	private String shippingAddress;

	@Column(name = "total_price", nullable = false)
	private BigDecimal totalPrice;

	@Enumerated(EnumType.STRING)
	@Column(name = "status", nullable = false)
	private OrderStatus status;

	@Column(name = "created_at", nullable = false)
	private LocalDateTime createdAt = LocalDateTime.now();

	@OneToMany(mappedBy = "order", cascade = CascadeType.ALL)
	private List<OrderItemEntity> orderItems = new ArrayList<>();

	public static OrderEntity create(UserEntity user, String shippingAddress, OrderStatus status, BigDecimal totalPrice) {
		OrderEntity order = new OrderEntity();
		order.setUser(user);
		order.setShippingAddress(shippingAddress);
		order.setStatus(status);
		order.setTotalPrice(totalPrice);
		return order;
	}
}