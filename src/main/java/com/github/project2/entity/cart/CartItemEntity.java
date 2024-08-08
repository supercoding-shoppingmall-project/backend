package com.github.project2.entity.cart;

import com.github.project2.entity.post.ProductEntity;
import com.github.project2.entity.post.ProductOptionEntity;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.Generated;
import org.hibernate.annotations.GenerationTime;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Builder
@Table(name = "CartItem")
public class CartItemEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "cart_id", nullable = false)
    private CartEntity cart;

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
    private BigDecimal totalPrice = BigDecimal.ZERO;

    @Column(name = "user_id", nullable = false)
    private Integer userId;

    @Column(name = "size", nullable = false)
    private Integer size;

    public static CartItemEntity create(CartEntity cart, ProductEntity product, Integer quantity, BigDecimal price, Integer userId, Integer size) {
        return CartItemEntity.builder()
            .cart(cart)
            .product(product)
            .quantity(quantity)
            .price(price)
            .totalPrice(price.multiply(BigDecimal.valueOf(quantity)))
            .userId(userId)
            .size(size)
            .build();
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
        this.totalPrice = this.price.multiply(BigDecimal.valueOf(quantity));
    }

    public void setTotalPrice(BigDecimal totalPrice) {
        this.totalPrice = totalPrice;
    }
}