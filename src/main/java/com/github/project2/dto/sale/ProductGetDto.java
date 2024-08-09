package com.github.project2.dto.sale;

import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Data

public class ProductGetDto {

    private String category;
    private String productName;
    private BigDecimal productPrice;
    private LocalDate endtime;
    private List<StockDto> stockDtos;
    private List<String> imageUrls;

    public ProductGetDto(String productName, BigDecimal productPrice, LocalDate endtime, String category, List<StockDto> stockDtos, List<String> imageUrls) {
        this.productName = productName;
        this.productPrice = productPrice;
        this.endtime = endtime;
        this.category = category;
        this.stockDtos = stockDtos;
        this.imageUrls = imageUrls;
    }
}
