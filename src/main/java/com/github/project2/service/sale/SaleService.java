package com.github.project2.service.sale;

import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.github.project2.config.S3Config;
import com.github.project2.dto.sale.CategoryDto;
import com.github.project2.dto.sale.ProductGetDto;
import com.github.project2.dto.sale.ProductSaveDto;
import com.github.project2.dto.sale.StockDto;
import com.github.project2.entity.category.CategoryEntity;
import com.github.project2.entity.product.ProductEntity;
import com.github.project2.entity.product.ProductImageEntity;
import com.github.project2.entity.product.ProductSizeEntity;
import com.github.project2.entity.user.UserEntity;
import com.github.project2.repository.category.CategoryRepository;
import com.github.project2.repository.product.ProductImageRepository;
import com.github.project2.repository.product.ProductRepository;
import com.github.project2.repository.product.ProductSizeRepository;
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
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class SaleService {

    @Autowired
    private final   S3Config s3Config;
    private final ProductImageRepository productImageRepository;
    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;
    private final ProductSizeRepository productSizeRepository;
    private final UserRepository userRepository;

    @Value("${cloud.aws.s3.bucket}")
    private String bucket;

    private String localLocation = "";


    public String imageUpload(MultipartFile file) throws IOException {
        String fileName = file.getOriginalFilename();
        String ext = fileName.substring(fileName.lastIndexOf("."));

        String uuidFileName = UUID.randomUUID() + ext;
        String localPath = localLocation + uuidFileName;

        File localFile = new File(localPath);
        file.transferTo(localFile);

        s3Config.amazonS3Client().putObject(new PutObjectRequest(bucket, uuidFileName, localFile).withCannedAcl(CannedAccessControlList.PublicRead));
        String s3Url = s3Config.amazonS3Client().getUrl(bucket, uuidFileName).toString();

        localFile.delete();

        return s3Url;
    }

    @Transactional
    public ProductEntity saveProduct(ProductSaveDto productSaveDto, List<MultipartFile> imageFiles) throws IOException {
        // 카테고리 찾기
        CategoryEntity categoryEntity = categoryRepository.findByCategoryName(productSaveDto.getCategory().getCategoryName())
                .orElseThrow(() -> new RuntimeException("카테고리를 찾을 수 없습니다."));

        UserEntity user = userRepository.findByEmail(productSaveDto.getSeller())
                .orElseThrow(() -> new IllegalArgumentException("판매자 email을 찾을 수 없습니다"));


        // 제품 엔티티 생성 및 저장
        ProductEntity productEntity = ProductEntity.builder()
                .productName(productSaveDto.getProductName())
                .productPrice(productSaveDto.getProductPrice())
                .productDescription(productSaveDto.getProductDescription())
                .createdAt(productSaveDto.getCreatedAt())
                .endtime(productSaveDto.getEndtime())
                .categoryEntity(categoryEntity)
                .user(user)

                .build();

        // 제품 엔티티 저장
        productEntity = productRepository.save(productEntity);

        // 재고 정보 저장
        for (StockDto stockDto : productSaveDto.getStockDtos()) {
            ProductSizeEntity productSizeEntity = new ProductSizeEntity();
            productSizeEntity.setProductId(productEntity);
            productSizeEntity.setSize(stockDto.getSize());
            productSizeEntity.setSizeStock(stockDto.getSizeStock());
            productSizeRepository.save(productSizeEntity);
        }

        // 이미지 정보 저장
        for (MultipartFile imageFile : imageFiles) {
            String imageUrl = imageUpload((MultipartFile) imageFile);

            ProductImageEntity productImageEntity = ProductImageEntity.builder()
                    .name(imageFile.getOriginalFilename())
                    .imageurl(imageUrl)
                    .product(productEntity)
                    .build();

            productImageRepository.save(productImageEntity);
        }

        return productEntity;
    }

    public List<ProductGetDto> getProductsBySellerEmail(String email) {

        UserEntity user = userRepository.findByEmail(email)
                .orElseThrow(() -> new IllegalArgumentException("판매자 email을 찾을 수 없습니다"));


        List<ProductEntity> products = productRepository.findByUserEmail(email);


        return products.stream().map(product -> {

            CategoryDto categoryDto = new CategoryDto(
                    product.getCategoryEntity().getId(),
                    product.getCategoryEntity().getCategoryName()
            );
            List<ProductImageEntity> productImages = productImageRepository.findByProduct(product);
            List<String> imageUrls = productImages.stream().map(ProductImageEntity::getImageurl).collect(Collectors.toList());

            // 재고 정보 가져오기
            List<StockDto> stockDtos = productSizeRepository.findByProductId(product).stream()
                    .map(sizeEntity -> new StockDto(sizeEntity.getSize(), sizeEntity.getSizeStock()))
                    .collect(Collectors.toList());

            return new ProductGetDto(
                    product.getProductName(),
                    product.getProductPrice(),
                    product.getEndtime(),
                    categoryDto,
                    stockDtos,
                    imageUrls
            );
        }).collect(Collectors.toList());
    }









}
