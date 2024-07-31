package com.github.project2.repository.post;

import com.github.project2.entity.post.ProductSizeEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ProductSizeRepository extends JpaRepository<ProductSizeEntity, Integer> {
    Optional<ProductSizeEntity> findByProductIdAndSize(Integer product, Integer size);
}
