package com.github.project2.service.post;

import com.github.project2.dto.post.ProductAllDto;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductService {
    public List<ProductAllDto> getProductAlls(int page, int pageSize) {

        Pageable pageable = PageRequest.of(page, pageSize);

        List<ProductAllDto> products =
    }
}
