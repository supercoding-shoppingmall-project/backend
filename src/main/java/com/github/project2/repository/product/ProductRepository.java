package com.github.project2.repository.product;


import com.github.project2.entity.product.ProductEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<ProductEntity, Integer> {

    List<ProductEntity> findByUserEmail(String email);

}
