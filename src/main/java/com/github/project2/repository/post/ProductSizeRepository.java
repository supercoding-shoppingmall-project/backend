package com.github.project2.repository.post;

import com.github.project2.entity.post.ProductEntity;
import com.github.project2.entity.post.ProductSizeEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface ProductSizeRepository  extends JpaRepository<ProductSizeEntity, Integer> {


    List<ProductSizeEntity> findByProductId(Integer productId);

    Optional<ProductSizeEntity> findByProductAndSize(ProductEntity product, Integer size);


}
