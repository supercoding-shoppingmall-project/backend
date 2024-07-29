package com.github.project2.dto.sale;


import com.fasterxml.jackson.annotation.JsonFormat;
import com.github.project2.entity.category.CategoryEntity;

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
    private CategoryEntity category;
    private String productName;
    private BigDecimal productPrice;
    private String productDescription;
    private Integer totalstock;

    private LocalDateTime createdAt;

    private LocalDateTime endtime;
    private List<StockDto> stockDtos; //판매종료날짜
}
