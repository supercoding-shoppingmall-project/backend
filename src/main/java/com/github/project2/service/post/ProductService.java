package com.github.project2.service.post;

import com.github.project2.dto.post.ProductAllDto;
import com.github.project2.entity.post.ProductEntity;
import com.github.project2.repository.post.ProductAllRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductAllRepository productAllRepository;

    public List<ProductAllDto> getProductAlls(int page, int pageSize) {
        Pageable pageable = PageRequest.of(page, pageSize);

        Page<ProductEntity> products = productAllRepository.findProductEntityByStockGreaterThanAndEndDateAfter(0, LocalDate.now(), pageable);

        return products.getContent().stream().map(this::convertToProductAllDto).collect(Collectors.toList());
    }

    private ProductAllDto convertToProductAllDto(ProductEntity productEntity) {
        return new ProductAllDto(
                productEntity.getId(),
                productEntity.getImages().stream().map(image -> image.getImageUrl()).collect(Collectors.toList()),
                productEntity.getName(),
                productEntity.getPrice(),
                productEntity.getStock(),
                productEntity.getEndDate()
        );
    }
}
