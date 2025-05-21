package com.example.plusteamproject.domain.search.controller;

import com.example.plusteamproject.common.ApiResponse;
import com.example.plusteamproject.domain.product.dto.ProductResponseDto;
import com.example.plusteamproject.domain.product.entity.Product;
import com.example.plusteamproject.domain.search.service.SearchService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Slice;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("products/search")
@RequiredArgsConstructor
public class SearchController {

    private final SearchService searchService;

    @GetMapping
    public ResponseEntity<ApiResponse<Slice<ProductResponseDto>>> findByProductName(@RequestParam String keyword) {

        Slice<ProductResponseDto> products = searchService.findByProductName(keyword);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(new ApiResponse<>("해당 상품을 조회합니다.", products));
    }
}
