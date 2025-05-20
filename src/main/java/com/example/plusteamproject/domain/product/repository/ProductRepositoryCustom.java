package com.example.plusteamproject.domain.product.repository;

import com.example.plusteamproject.domain.product.dto.ProductListResponseDto;
import com.example.plusteamproject.domain.product.entity.Product;
import com.example.plusteamproject.domain.product.entity.ProductCategory;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;

import java.util.Optional;

public interface ProductRepositoryCustom {

    Optional<Product> getProductByIdWithUser(Long id);

    Slice<ProductListResponseDto> findCursorProductBySizeAndCategory(ProductCategory category, Long lastId, Pageable pageable);

}
