package com.github.project2.repository.product;

import com.github.project2.entity.product.ProductEntity;
import com.github.project2.entity.product.ProductImageEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductImageRepository extends JpaRepository<ProductImageEntity,Integer> {

    List<ProductImageEntity> findByProduct(ProductEntity product);
}
