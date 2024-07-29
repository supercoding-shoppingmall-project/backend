package com.github.project2.repository.post;

import com.github.project2.entity.post.ProductCategoryEntity;
import com.github.project2.entity.post.ProductEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<ProductEntity, Integer> {

    Page<ProductEntity> findProductEntityByTotalStockGreaterThanAndEndDateAfter(int totalStock, LocalDate endDate, Pageable pageable);

    Page<ProductEntity> findProductCategoryEntityByEndDateAfterAndCategoryId_CategoryId(LocalDate endDate, Integer categoryId, Pageable pageable);

    List<ProductEntity> findByProductId(Integer id);

    List<ProductEntity> findByCategoryId_CategoryId(Integer categoryId);
}
