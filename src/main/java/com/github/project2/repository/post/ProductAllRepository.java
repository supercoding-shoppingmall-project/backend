package com.github.project2.repository.post;

import com.github.project2.entity.post.ProductEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Repository
public interface ProductAllRepository extends JpaRepository<ProductEntity, Integer> {

    Page<ProductEntity> findProductEntityByStockGreaterThanAndEndDateAfter(int stock, LocalDate endDate, Pageable pageable);
}
