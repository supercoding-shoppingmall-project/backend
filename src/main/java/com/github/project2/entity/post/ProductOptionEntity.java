package com.github.project2.entity.post;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.relational.core.sql.In;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "ProductOption")
public class ProductOptionEntity {
    @Id@GeneratedValue
    @Column(name = "id")
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id", nullable = false)
    private ProductEntity productId;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "additional_price", nullable = false, precision = 10, scale = 2)
    private BigDecimal addtionalPrice;

    @Column(name = "created_at")
    private LocalDateTime createdAt;
}
