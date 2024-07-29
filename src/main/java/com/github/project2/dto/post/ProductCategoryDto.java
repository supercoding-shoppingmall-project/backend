package com.github.project2.dto.post;

import com.github.project2.entity.post.ProductCategoryEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ProductCategoryDto {
    private Integer id;
    private String sellerEmail;
    private List<String> imageUrls;
    private Integer category_id;
    private String name;
    private BigDecimal price;
    private Integer totalStock;
    private LocalDate endDate;
    private String categoryName;
}
