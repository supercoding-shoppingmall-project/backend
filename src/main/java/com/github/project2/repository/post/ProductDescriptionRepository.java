package com.github.project2.repository.post;

import com.github.project2.entity.post.ProductDescriptionEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductDescriptionRepository extends JpaRepository<ProductDescriptionEntity, Integer> {
}
