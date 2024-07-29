package com.github.project2.entity.product;


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
@Table(name = "ProductSize")
public class ProductSizeEntity {

    @Id@GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer Id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id", nullable = false)
    private ProductEntity ProductId;

    @Column(name = "size", nullable = false)
    private Integer size;

    @Column(name = "size_stock", nullable = false)
    private Integer sizeStock;
}
