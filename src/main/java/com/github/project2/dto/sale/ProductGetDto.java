package com.github.project2.dto.sale;

import com.github.project2.entity.category.CategoryEntity;
import com.github.project2.entity.user.UserEntity;
import lombok.*;
import org.springframework.web.multipart.MultipartFile;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Data

public class ProductGetDto {


    private CategoryDto category;
    private String productName;
    private BigDecimal productPrice;
    private LocalDateTime endtime;
    private List<StockDto> stockDtos;
    private List<String> imageUrls;

    public ProductGetDto(String productName, BigDecimal productPrice,  LocalDateTime endtime, CategoryDto category,  List<StockDto> stockDtos, List<String> imageUrls) {
        this.productName = productName;
        this.productPrice = productPrice;
        this.endtime = endtime;
        this.category = category;
        this.stockDtos = stockDtos;
        this.imageUrls = imageUrls;
    }
}
