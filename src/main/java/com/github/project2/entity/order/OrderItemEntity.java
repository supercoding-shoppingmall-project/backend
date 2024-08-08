package com.github.project2.entity.order;


import com.fasterxml.jackson.annotation.JsonBackReference;
import com.github.project2.entity.post.ProductEntity;
import com.github.project2.entity.post.ProductOptionEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "OrderItem")
public class OrderItemEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	@ManyToOne
	@JoinColumn(name = "order_id", nullable = false)
	private OrderEntity order;

	@ManyToOne
	@JoinColumn(name = "product_id", nullable = false)
	private ProductEntity product;

	@ManyToOne
	@JoinColumn(name = "product_option_id", nullable = true)
	private ProductOptionEntity productOption;

	@Column(name = "quantity", nullable = false)
	private Integer quantity;

	@Column(name = "price", nullable = false)
	private BigDecimal price;

	@Column(name = "total_price", nullable = false)
	private BigDecimal totalPrice;

	@Column(name = "created_at", nullable = false)
	private LocalDateTime createdAt = LocalDateTime.now();

	public static OrderItemEntity create(OrderEntity order, ProductEntity product, ProductOptionEntity productOption, Integer quantity, BigDecimal price) {
		OrderItemEntity orderItem = new OrderItemEntity();
		orderItem.setOrder(order);
		orderItem.setProduct(product);
		orderItem.setProductOption(productOption);
		orderItem.setQuantity(quantity);
		orderItem.setPrice(price);
		orderItem.setTotalPrice(price.multiply(BigDecimal.valueOf(quantity)));
		return orderItem;
	}
}
