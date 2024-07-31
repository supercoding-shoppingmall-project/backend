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
@Table(name="CartItem") // 장바구니 항목 테이블 매핑
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

//    @Column(name = "created_at", nullable = false, updatable = false)
//    @Generated(GenerationTime.INSERT)
//    private LocalDateTime createdAt = LocalDateTime.now();
}
