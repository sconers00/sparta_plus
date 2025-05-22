package com.example.plusteamproject.domain.searchV1.controller;

import com.example.plusteamproject.common.ApiResponse;
import com.example.plusteamproject.domain.product.dto.ProductResponseDto;
import com.example.plusteamproject.domain.searchV1.dto.PopularSearchResponseDto;
import com.example.plusteamproject.domain.searchV1.service.SearchServiceV1;
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
@RequestMapping("/v1/products/search")
@RequiredArgsConstructor
public class SearchControllerV1 {

    private final SearchServiceV1 searchService;

    /**
     * 검색어를 통한 상품 조회
     * @param keyword 검색 키워드
     * @return 해당 키워드가 들어간 상품들의 정보
     */
    @GetMapping
    public ResponseEntity<ApiResponse<Slice<ProductResponseDto>>> findByProductName(@RequestParam String keyword) {

        Slice<ProductResponseDto> products = searchService.findByProductName(keyword);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(new ApiResponse<>("해당 상품을 조회합니다.", products));
    }

    /**
     * 인기 검색어 조회
     * @return 1위부터 10위까지의 인기검색어 조회
     */
    @GetMapping("/popular")
    public ResponseEntity<ApiResponse<List<PopularSearchResponseDto>>> findByPopulation() {

        List<PopularSearchResponseDto> top10 = searchService.findByPopulation();

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(new ApiResponse<>("인기 검색어 순위 10위까지 조회합니다.", top10));
    }
}
