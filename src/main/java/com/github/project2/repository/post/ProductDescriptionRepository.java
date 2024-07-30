package com.github.project2.repository.post;


import com.github.project2.entity.post.ProductDescritptionEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductDescriptionRepository extends JpaRepository<ProductDescritptionEntity, Integer> {
}
