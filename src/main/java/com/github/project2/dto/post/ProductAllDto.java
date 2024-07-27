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
public class ProductAllDto {
    private Integer id;
    private List<String> imageUrls;
    private String name;
    private BigDecimal price;
    private Integer stock;
    private LocalDate endDate;
}
