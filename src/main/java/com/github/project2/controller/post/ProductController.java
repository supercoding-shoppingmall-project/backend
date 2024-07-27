package com.github.project2.controller.post;


import com.github.project2.dto.post.ProductAllDto;
import com.github.project2.dto.post.ProductCategoryDto;
import com.github.project2.service.post.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/product")
@RequiredArgsConstructor    // 생성자 대신
public class ProductController {

    private final ProductService productService;
    // reponseEntity
    @GetMapping("/all")
    public ResponseEntity<List<ProductAllDto>> getProductAlls(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "12") int pageSize){
        List<ProductAllDto> products = productService.getProductAlls(page, pageSize);
        return ResponseEntity.ok(products);
    }

    @GetMapping("/category/{category}")
    public ResponseEntity<List<ProductCategoryDto>> getProductCategorys(
            @PathVariable String category,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "12") int pageSize){
        List<ProductCategoryDto> productCategory = productService.getProductCategorys(category, page, pageSize);
        return ResponseEntity.ok(productCategory);
    }
}
