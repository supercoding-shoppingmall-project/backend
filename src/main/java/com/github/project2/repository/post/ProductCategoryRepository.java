package com.github.project2.repository.post;


import com.github.project2.entity.post.ProductCategoryEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ProductCategoryRepository extends JpaRepository<ProductCategoryEntity, Integer> {
    Optional<ProductCategoryEntity> findByName(String name);
}
