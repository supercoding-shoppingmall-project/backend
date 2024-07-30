package com.github.project2.controller.sale;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.github.project2.dto.sale.ProductGetDto;
import com.github.project2.dto.sale.ProductSaveDto;

import com.github.project2.dto.sale.StockDto;
import com.github.project2.entity.post.ProductEntity;
import com.github.project2.entity.post.ProductSizeEntity;
import com.github.project2.service.sale.SaleService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.MultipartRequest;

import java.io.IOException;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class SalesController {

    @Autowired
    private  SaleService saleService;


    //판매자 상품 등록
    @PostMapping(value = "/sell/save", consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.MULTIPART_FORM_DATA_VALUE})
    public ResponseEntity<String> createProduct(
            @RequestPart("product") String productJson,
            @RequestPart("images") List<MultipartFile> images) throws IOException {


        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        ProductSaveDto productSaveDto = objectMapper.readValue(productJson, ProductSaveDto.class);


        ProductEntity productEntity = saleService.saveProduct(productSaveDto, images);

        return ResponseEntity.ok("상품 등록에 성공했습니다");
    }
    //판매자 상품 조회
    @GetMapping("/sell/{email}")
    public ResponseEntity<List<ProductGetDto>> getProductBySellerEmail(@PathVariable String email) {
        List<ProductGetDto> products = saleService.getProductsBySellerEmail(email);
        return ResponseEntity.ok(products);
    }

    @PutMapping("/sell/update/{productName}")
    public ResponseEntity<String> updateStock(
            @PathVariable String productName,
            @RequestBody StockDto stockDto) {

        ProductSizeEntity updatedProductSize = saleService.updateStock(productName, stockDto);
        return ResponseEntity.ok("재고 수정에성공했습니다.");
    }






}
