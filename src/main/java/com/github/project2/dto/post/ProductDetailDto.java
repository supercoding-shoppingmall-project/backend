package com.github.project2.dto.post;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ProductDetailDto {
    private Integer id;
    private List<String> imageUrls;
    private String category;
    private String name;
    private BigDecimal price;
    private List<Integer> size;
    private List<Integer> sizeStock;
    private String description;
    private LocalDate endDate;
    private Integer views;  // 리뷰 대체
    private Integer sales;  // 평점 대체 (Double 할것)
}
