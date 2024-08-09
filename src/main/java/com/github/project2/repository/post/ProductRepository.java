package com.github.project2.repository.post;

import com.github.project2.entity.post.ProductEntity;
import com.github.project2.entity.user.UserEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface ProductRepository extends JpaRepository<ProductEntity, Integer> {
    Page<ProductEntity> findProductEntityByTotalStockGreaterThanAndEndDateAfter(int totalStock, LocalDate endDate, Pageable pageable);
    Page<ProductEntity> findProductCategoryEntityByEndDateAfterAndCategoryId_CategoryId(LocalDate endDate, Integer categoryId, Pageable pageable);
    Optional<ProductEntity> findById(Integer id);
    List<ProductEntity> findByCategoryId_CategoryId(Integer categoryId);
    List<ProductEntity> findBySellerId(UserEntity user);
    Optional<ProductEntity> findByName(String productName);
}
