package com.github.project2.dto.sale;

import com.fasterxml.jackson.annotation.JsonFormat;


import com.github.project2.entity.post.ProductCategoryEntity;
import lombok.*;


import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Data
public class ProductSaveDto {

    private String seller;
    private String category;
    private String productName;
    private BigDecimal productPrice;
    private List<ProductDescriptionDto>  descriptions;
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate createdAt;
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate endtime;
    private List<StockDto> stockDtos;
}
