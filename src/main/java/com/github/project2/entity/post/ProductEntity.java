package com.github.project2.entity.post;

import com.github.project2.entity.user.UserEntity;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.Formula;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "Product")
@Builder
public class ProductEntity {
    @Id@GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer Id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "description", columnDefinition = "TEXT")
    private String description;

    @Column(name = "price", nullable = false, precision = 10, scale = 2)
    private BigDecimal price;

    @Column(name = "review", columnDefinition = "INT DEFAULT 0")
    private Integer review;
    // sales -> 평점으로 대체
    @Column(name = "grade", columnDefinition = "INT DEFAULT 0")
    private Double grade;




    @Column(name = "end_date")
    private LocalDate endDate;

    @Column(name = "created_at", nullable = false)
    private LocalDate createAt;// @@@@@

    @Formula("(select sum(ps.size_stock) from ProductSize ps where ps.product_id = id)")
    private Integer totalStock;

    @OneToMany(mappedBy = "product", fetch = FetchType.LAZY)
    private List<ProductSizeEntity> sizes;

    @OneToMany(mappedBy = "product", fetch = FetchType.LAZY)
    private List<ProductDescriptionEntity> descriptions;

    @OneToMany(mappedBy = "product", fetch = FetchType.LAZY)
    private List<ProductImageEntity> images;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "seller_id")
    private UserEntity sellerId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id")
    private ProductCategoryEntity categoryId;
}