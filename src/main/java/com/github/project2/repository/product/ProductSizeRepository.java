package com.github.project2.repository.product;

import com.github.project2.entity.product.ProductSizeEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductSizeRepository  extends JpaRepository<ProductSizeEntity, Integer> {
}
