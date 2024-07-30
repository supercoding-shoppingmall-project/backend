package com.github.project2.entity.post;

import com.github.project2.entity.user.UserEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Formula;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "Product")
public class ProductEntity {
    @Id@GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer productId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "seller_id")
    private UserEntity sellerId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id")
    private ProductCategoryEntity categoryId;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "price", nullable = false, precision = 10, scale = 2)
    private BigDecimal price;

    @Column(name = "size", length = 20)
    private String size;    // 안쓸거

    @Column(name = "stock", nullable = false)
    private Integer stock;
    // 리뷰로 대체
    @Column(name = "views", columnDefinition = "INT DEFAULT 0")
    private Integer views;
    // 평점으로 대체
    @Column(name = "sales", columnDefinition = "INT DEFAULT 0")
    private Integer sales;

    @Column(name = "end_date")
    private LocalDate endDate;

    @Column(name = "created_at", nullable = false)
    private LocalDate createAt;

    @OneToMany(mappedBy = "ProductId", fetch = FetchType.LAZY)
    private List<ProductSizeEntity> sizeId;

    @OneToMany(mappedBy = "productId", fetch = FetchType.LAZY)
    private List<ProductImageEntity> images;    // @@@@@

    @Formula("(select sum(ps.size_stock) from ProductSize ps where ps.product_id = id)")
    private Integer totalStock;
}
