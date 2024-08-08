package com.github.project2.repository.post;

import com.github.project2.entity.post.ProductEntity;
import com.github.project2.entity.post.ProductSizeEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
@Repository
public interface ProductSizeRepository extends JpaRepository<ProductSizeEntity, Integer> {
    Optional<ProductSizeEntity> findByProductAndSize(ProductEntity product, Integer size);
    List<ProductSizeEntity> findByProductId(Integer productId);
    Optional<ProductSizeEntity> findByProductIdAndSize(Integer productId, Integer size);
}
