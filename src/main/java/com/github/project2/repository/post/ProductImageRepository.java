package com.github.project2.repository.post;

import com.github.project2.entity.post.ProductEntity;
import com.github.project2.entity.post.ProductImageEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductImageRepository extends JpaRepository<ProductImageEntity,Integer> {
    List<ProductImageEntity> findByProduct(ProductEntity product);
}
