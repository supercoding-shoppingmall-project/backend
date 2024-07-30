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
    private String sellerEmail;
    private List<String> imageUrls;
    private String category;
    private String name;
    private BigDecimal price;
    private List<Integer> size;
    private List<Integer> sizeStock;
    private List<String> description;
    private LocalDate endDate;
    private Integer review;
    private Double grade;
}
