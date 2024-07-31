package com.github.project2.service.sale;

import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.github.project2.config.S3Config;
import com.github.project2.dto.sale.*;


import com.github.project2.entity.post.*;
import com.github.project2.entity.user.UserEntity;
import com.github.project2.repository.post.*;

import com.github.project2.repository.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;


import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class SaleService {

    @Autowired
    private final S3Config s3Config;
    private final ProductImageRepository productImageRepository;
    private final ProductRepository productRepository;
    private final ProductCategoryRepository ProductCategoryRepository;
    private final ProductSizeRepository productSizeRepository;
    private final ProductDescriptionRepository productDescriptionRepository;
    private final UserRepository userRepository;

    @Value("${cloud.aws.s3.bucket}")
    private String bucket;

//    private String localLocation = "C:\\Users\\jk059\\OneDrive\\바탕 화면\\shoe";


    public String imageUpload(MultipartFile file) throws IOException {
        // 원본 파일 이름에서 확장자 추출
        String fileName = file.getOriginalFilename();
        String ext = fileName.substring(fileName.lastIndexOf("."));

        // 고유한 파일 이름 생성
        String uuidFileName = UUID.randomUUID() + ext;

        // ObjectMetadata 설정
        ObjectMetadata metadata = new ObjectMetadata();
        metadata.setContentType(file.getContentType());  // 파일의 Content-Type 설정
        metadata.setContentLength(file.getSize());       // 파일 크기 설정

        // 메모리에서 직접 S3로 업로드
        PutObjectRequest putObjectRequest = new PutObjectRequest(
                bucket,
                uuidFileName,
                file.getInputStream(),
                metadata
        ).withCannedAcl(CannedAccessControlList.PublicRead);  // 공개 읽기 권한 설정

        s3Config.amazonS3Client().putObject(putObjectRequest);

        // 업로드된 파일의 S3 URL 반환
        return s3Config.amazonS3Client().getUrl(bucket, uuidFileName).toString();
    }

    @Transactional
    public ProductEntity saveProduct(ProductSaveDto productSaveDto, List<MultipartFile> imageFiles) throws IOException {
        // 카테고리 찾기
        ProductCategoryEntity category = ProductCategoryRepository.findByName(productSaveDto.getCategory())
                .orElseThrow(() -> new RuntimeException("카테고리를 찾을 수 없습니다."));

        UserEntity user = userRepository.findByEmail(productSaveDto.getSeller())
                .orElseThrow(() -> new IllegalArgumentException("판매자 email을 찾을 수 없습니다"));


        // 제품 엔티티 생성 및 저장

        ProductEntity productEntity = ProductEntity.builder()
                .name(productSaveDto.getProductName())
                .price(productSaveDto.getProductPrice())
                .createAt(productSaveDto.getCreatedAt())
                .endDate(productSaveDto.getEndtime())
                .categoryId(category)
                .sellerId(user)
                .build();

        // 제품 엔티티 저장
        productEntity = productRepository.save(productEntity);

        // 재고 정보 저장
        for (StockDto stockDto : productSaveDto.getStockDtos()) {
            ProductSizeEntity productSizeEntity = new ProductSizeEntity();
            productSizeEntity.setProduct(productEntity);
            productSizeEntity.setSize(stockDto.getSize());
            productSizeEntity.setSizeStock(stockDto.getSizeStock());
            productSizeRepository.save(productSizeEntity);
        }

        // 설명 저장
        for (ProductDescriptionDto productDescriptionDto : productSaveDto.getDescriptions()){
            ProductDescriptionEntity productDescriptionEntity = new ProductDescriptionEntity();
            productDescriptionEntity.setProduct(productEntity);
            productDescriptionEntity.setDescription(productDescriptionDto.getDescription());
            productDescriptionRepository.save(productDescriptionEntity);
        }

        // 이미지 정보 저장
        for (MultipartFile imageFile : imageFiles) {
            String imageUrl = imageUpload((MultipartFile) imageFile);

            ProductImageEntity productImageEntity = ProductImageEntity.builder()
                    .name(imageFile.getOriginalFilename())
                    .imageUrl(imageUrl)
                    .product(productEntity)
                    .build();

            productImageRepository.save(productImageEntity);
        }

        return productEntity;
    }

    public List<ProductGetDto> getProductsBySellerEmail(String email) {
        UserEntity user = userRepository.findByEmail(email)
                .orElseThrow(() -> new IllegalArgumentException("판매자 email을 찾을 수 없습니다"));

        List<ProductEntity> products = productRepository.findBySellerId(user);

        // 상품이 없는 경우 예외 처리
        if (products.isEmpty()) {
            throw new RuntimeException("해당 판매자의 제품을 찾을 수 없습니다");
        }

        return products.stream()
                .map(product -> {

                    Integer productId = product.getId();
                    List<ProductSizeEntity> sizes = productSizeRepository.findByProductId(productId);
                    List<ProductImageEntity> images = productImageRepository.findByProduct(product);

                    List<StockDto> stockDtos = sizes.stream()
                            .map(size -> new StockDto(size.getSize(), size.getSizeStock()))
                            .collect(Collectors.toList());

                    List<String> imageUrls = images.stream()
                            .map(ProductImageEntity::getImageUrl)
                            .collect(Collectors.toList());

                    return new ProductGetDto(
                            product.getCategoryId().getName(),
                            product.getName(),
                            product.getPrice(),
                            product.getEndDate(),
                            stockDtos,
                            imageUrls
                    );
                })
                .collect(Collectors.toList());
    }

    public ProductSizeEntity updateStock(String productName, StockDto stockDto) {

        ProductEntity product = productRepository.findByName(productName)
                .orElseThrow(() -> new RuntimeException("제품의 이름 없음"));


        ProductSizeEntity productSize = productSizeRepository.findByProductAndSize(product, stockDto.getSize())
                .orElseThrow(() -> new RuntimeException("사이즈 없음"));


        productSize.setSizeStock(stockDto.getSizeStock());


        return productSizeRepository.save(productSize);
    }




}



