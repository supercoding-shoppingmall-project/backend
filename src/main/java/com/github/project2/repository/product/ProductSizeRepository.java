package com.github.project2.repository.product;

import com.github.project2.entity.product.ProductEntity;
import com.github.project2.entity.product.ProductSizeEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ProductSizeRepository  extends JpaRepository<ProductSizeEntity, Integer> {

    @Query("SELECT p FROM ProductSizeEntity p WHERE p.ProductId = :product")
    List<ProductSizeEntity> findByProductId(@Param("product") ProductEntity product);
}
