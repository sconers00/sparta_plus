package com.example.plusteamproject.domain.product.service;

import com.example.plusteamproject.common.Status;
import com.example.plusteamproject.domain.product.dto.ProductListResponseDto;
import com.example.plusteamproject.domain.product.dto.ProductResponseDto;
import com.example.plusteamproject.domain.product.dto.ProductSaveRequestDto;
import com.example.plusteamproject.domain.product.dto.ProductUpdateRequestDto;
import com.example.plusteamproject.domain.product.entity.Product;
import com.example.plusteamproject.domain.product.entity.ProductCategory;
import com.example.plusteamproject.domain.product.repository.ProductRepository;
import com.example.plusteamproject.security.CustomUserDetail;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;


    public ProductResponseDto createProduct(CustomUserDetail user, ProductSaveRequestDto dto) {
        Product product = Product.of(dto.getProductCategory(), dto.getName(), dto.getContent(), dto.getPrice(), dto.getQuantity(), user.getUser());
        Product saveProduct = productRepository.save(product);

        return new ProductResponseDto(saveProduct);

    }


    public ProductResponseDto getProduct(Long productId) {

        Product findProduct = productRepository.getTodoByIdWithUser(productId)
                .orElseThrow(() -> new RuntimeException("재품이 존재하지 않습니다"));

        return new ProductResponseDto(findProduct);
    }


    public Slice<ProductListResponseDto> findCursorProductBySizeAndCategory(ProductCategory category, Long lastId, int size) {

        Pageable pageable = PageRequest.of(0, size);

        return productRepository.findCursorProductBySizeAndCategory(category, lastId, pageable);
    }


    public ProductResponseDto updateProduct(CustomUserDetail userDetail, Long productId, ProductUpdateRequestDto dto) {

        Product findProduct = productRepository.getTodoByIdWithUser(productId)
                .orElseThrow(() -> new RuntimeException("재품이 존재하지 않습니다"));

        if (userDetail.getUser().getId().equals(findProduct.getUser().getId())) {
            findProduct.update(dto);
        } else {
            throw new RuntimeException("해당 제품의 생성자만 수정 가능합니다");
        }

        return new ProductResponseDto(findProduct);
    }


    public void deleteProduct(CustomUserDetail userDetail, Long productId) {

        Product findProduct = productRepository.getTodoByIdWithUser(productId)
                .orElseThrow(() -> new RuntimeException("재품이 존재하지 않습니다"));

        if (!(userDetail.getUser().getId().equals(findProduct.getUser().getId()))) {
            throw new RuntimeException("해당 제품의 생성자만 삭제 가능합니다");
        }

        findProduct.updateDeleteStatus(Status.NON_EXIST.isValue());
        productRepository.save(findProduct);
    }
}
