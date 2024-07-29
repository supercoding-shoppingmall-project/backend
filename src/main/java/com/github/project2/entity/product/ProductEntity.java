package com.github.project2.entity.product;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.github.project2.entity.category.CategoryEntity;
import com.github.project2.entity.user.UserEntity;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.Formula;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "Product")
@Builder
public class ProductEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "name" , length = 50)
    private String productName;

    @Column(name = "price" , length = 10)
    private BigDecimal productPrice;

    @Column(name = "description", length = 100)
    private String productDescription;


//    @Column(name = "views", length = 100)
//    private Integer views;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "end_date")
    private LocalDateTime endtime;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "seller_id")
    private UserEntity user;

    @ManyToOne
    @JoinColumn(name = "category_id")
    private CategoryEntity categoryEntity;

    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL)
    private List<ProductImageEntity> images;



    @Formula("(select sum(ps.size_stock) from ProductSize ps where ps.product_id = id)")
    private Integer totalStock;
}
