package com.github.project2.entity.cart;

import com.github.project2.entity.post.ProductCategoryEntity;
import com.github.project2.entity.post.ProductEntity;
import com.github.project2.entity.user.UserEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name="CartItem") // 장바구니 항목 테이블 매핑
public class CartItemEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY)  // 사용자와 N:1
    @JoinColumn(name = "user_id")
    private UserEntity user;

    @ManyToOne(fetch = FetchType.LAZY)  // 상품과 N:1
    @JoinColumn(name = "product_id")
    private ProductEntity product;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id")
    private ProductCategoryEntity productCategory;

    @Column(name = "size", nullable = false, length = 20)
    private String size;

    @Column(name = "quantity", nullable = false)
    private Integer quantity;
}
