package com.example.plusteamproject.domain.product.controller;

import com.example.plusteamproject.common.ApiResponse;
import com.example.plusteamproject.domain.product.dto.ProductListResponseDto;
import com.example.plusteamproject.domain.product.dto.ProductResponseDto;
import com.example.plusteamproject.domain.product.dto.ProductSaveRequestDto;
import com.example.plusteamproject.domain.product.dto.ProductUpdateRequestDto;
import com.example.plusteamproject.domain.product.entity.ProductCategory;
import com.example.plusteamproject.domain.product.service.ProductService;
import com.example.plusteamproject.security.CustomUserDetail;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Slice;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/products")
    public ResponseEntity<ApiResponse<ProductResponseDto>> createProduct(
        @AuthenticationPrincipal CustomUserDetail userDetail,
        @RequestBody ProductSaveRequestDto productSaveRequestDto
    ){
        return ResponseEntity
            .status(HttpStatus.CREATED)
            .body(new ApiResponse<>("제품을 생성했습니다.",productService.createProduct(userDetail,productSaveRequestDto)));
    }

    @GetMapping("/products/{productId}")
    public ResponseEntity<ApiResponse<ProductResponseDto>> getProduct(
        @PathVariable(name = "productId") Long productId
    ) {
        return ResponseEntity
            .status(HttpStatus.OK)
            .body(new ApiResponse<>("제품의 상세정보 입니다.",productService.getProduct(productId)));
    }

    @GetMapping("/products")
    public ResponseEntity<ApiResponse<Slice<ProductListResponseDto>>>findCursorProductBySizeAndCategory(
        @RequestParam(required = false)ProductCategory category,
        @RequestParam(required = false)Long lastId,
        @RequestParam(defaultValue = "5") int size){

        return ResponseEntity
            .status(HttpStatus.OK)
            .body(new ApiResponse<>(category.getDisplayName()+" 목록 입니다.",productService.findCursorProductBySizeAndCategory(category,lastId,size)));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PatchMapping("/products/{productId}")
    public ResponseEntity<ApiResponse<ProductResponseDto>> updateProduct(
        @AuthenticationPrincipal CustomUserDetail userDetail,
        @PathVariable(name = "productId") Long productId,
        @RequestBody ProductUpdateRequestDto productUpdateRequestDto
        ) {
        return ResponseEntity
            .status(HttpStatus.OK)
            .body(new ApiResponse<>("제품 정보를 수정하였습니다.",productService.updateProduct(userDetail,productId,productUpdateRequestDto)));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/products/{productId}")
    public ResponseEntity<ApiResponse<Void>> deleteProduct(
        @AuthenticationPrincipal CustomUserDetail userDetail,
        @PathVariable(name ="productId") Long productId
    ){
        productService.deleteProduct(userDetail,productId);

        return ResponseEntity
            .status(HttpStatus.OK)
            .body(new ApiResponse<>("제품을 삭제했습니다."));
    }
}
