package com.example.plusteamproject.domain.searchV2.controller;

import com.example.plusteamproject.common.ApiResponse;
import com.example.plusteamproject.domain.product.dto.ProductResponseDto;
import com.example.plusteamproject.domain.searchV1.dto.PopularSearchResponseDto;
import com.example.plusteamproject.domain.searchV2.service.SearchServiceV2;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Slice;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/v2/products/search")
@RequiredArgsConstructor
public class SearchControllerV2 {

    private final SearchServiceV2 searchService;

    @GetMapping
    public ResponseEntity<ApiResponse<Slice<ProductResponseDto>>> findByProductName(@RequestParam String keyword) {

        searchService.increaseSearchCount(keyword);
        Slice<ProductResponseDto> products = searchService.findByProductName(keyword);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(new ApiResponse<>("해당 상품을 조회합니다.", products));
    }

    @GetMapping("/popular")
    public ResponseEntity<ApiResponse<List<PopularSearchResponseDto>>> findByPopulation() {

        List<PopularSearchResponseDto> top10 = searchService.findByPopulation();

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(new ApiResponse<>("인기 검색어 순위 10위까지 조회합니다.", top10));
    }

}
