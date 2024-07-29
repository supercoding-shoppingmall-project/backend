package com.github.project2.service.post;

import com.github.project2.dto.post.HeaderDto;
import com.github.project2.dto.post.ProductAllDto;
import com.github.project2.dto.post.ProductCategoryDto;
import com.github.project2.dto.post.ProductDetailDto;
import com.github.project2.entity.post.ProductEntity;
import com.github.project2.entity.post.ProductSizeEntity;
import com.github.project2.repository.post.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;

    public List<ProductAllDto> getProductAlls(int page, int pageSize) {
        Pageable pageable = PageRequest.of(page, pageSize);

        Page<ProductEntity> products = productRepository.findProductEntityByTotalStockGreaterThanAndEndDateAfter(0, LocalDate.now(), pageable);

        return products.getContent().stream().map(this::convertToProductAllDto).collect(Collectors.toList());
    }

    private ProductAllDto convertToProductAllDto(ProductEntity productEntity) {
        return new ProductAllDto(
                productEntity.getProductId(),
                productEntity.getImages().stream().map(image -> image.getImageUrl()).collect(Collectors.toList()),
                productEntity.getName(),
                productEntity.getPrice(),
                productEntity.getTotalStock(),
                productEntity.getEndDate()
        );
    }
    public List<ProductCategoryDto> getProductCategorys(Integer category, int page, int pageSize) {
        Pageable pageable = PageRequest.of(page, pageSize);

        Page<ProductEntity> productCategorys = productRepository.findProductCategoryEntityByEndDateAfterAndCategoryId_CategoryId(LocalDate.now(), category, pageable);
        //  페이징된 데이터의 내용을 List 형태로 반환하기 위해 getContent() 메서드
        return productCategorys.getContent().stream().map(this::convertToProductCategoryDto).collect(Collectors.toList());
    }

    private ProductCategoryDto convertToProductCategoryDto(ProductEntity productEntity){
        return new ProductCategoryDto(
                productEntity.getProductId(),
                productEntity.getImages().stream().map(image -> image.getImageUrl()).collect(Collectors.toList()),
                productEntity.getCategoryId().getCategoryId(),
                productEntity.getName(),
                productEntity.getPrice(),
                productEntity.getTotalStock(),
                productEntity.getEndDate(),
                productEntity.getCategoryId().getName()
        );
    }

    public List<ProductDetailDto> getProductDetails(Integer id) {
        List<ProductEntity> productDetails = productRepository.findByProductId(id);
        return productDetails.stream().map(this::convertToProductDetailDto).collect(Collectors.toList());
    }

    private ProductDetailDto convertToProductDetailDto(ProductEntity productEntity){
        return new ProductDetailDto(
                productEntity.getProductId(),
                productEntity.getImages().stream().map(image -> image.getImageUrl()).collect(Collectors.toList()),
                productEntity.getCategoryId().getName(),
                productEntity.getName(),
                productEntity.getPrice(),
                productEntity.getSizeId().stream().map(ProductSizeEntity::getSize).collect(Collectors.toList()),
                productEntity.getSizeId().stream().map(ProductSizeEntity::getSizeStock).collect(Collectors.toList()),
                productEntity.getDescription(),
                productEntity.getEndDate(),
                productEntity.getViews(),
                productEntity.getSales()
        );
    }

    public List<HeaderDto> getHeaderInfo(Integer category) {
        List<ProductEntity> headerInfo = productRepository.findByCategoryId_CategoryId(category);
        return headerInfo.stream().map(this::convertToHeaderDto).collect(Collectors.toList());
    }

    private HeaderDto convertToHeaderDto(ProductEntity productEntity){
        return new HeaderDto(
                productEntity.getProductId(),
                productEntity.getCategoryId().getCategoryId()
        );
    }
}
