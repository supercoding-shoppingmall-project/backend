package com.github.project2.repository.post;

import com.github.project2.entity.post.ProductCategoryEntity;
import com.github.project2.entity.post.ProductEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;

@Repository
public interface ProductRepository extends JpaRepository<ProductEntity, Integer> {

    Page<ProductEntity> findProductEntityByStockGreaterThanAndEndDateAfter(int stock, LocalDate endDate, Pageable pageable);

    Page<ProductEntity> findProductCategoryEntityByStockGreaterThanAndEndDateAfterAndCategoryName(int stock, LocalDate endDate, String categoryName, Pageable pageable);
}
