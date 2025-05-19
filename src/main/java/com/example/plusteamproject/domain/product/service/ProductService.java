package com.example.plusteamproject.domain.product.service;

import com.example.plusteamproject.common.Status;
import com.example.plusteamproject.domain.product.dto.ProductResponseDto;
import com.example.plusteamproject.domain.product.dto.ProductSaveRequestDto;
import com.example.plusteamproject.domain.product.dto.ProductUpdateRequestDto;
import com.example.plusteamproject.domain.product.entity.Product;
import com.example.plusteamproject.domain.product.repository.ProductRepository;
import com.example.plusteamproject.security.CustomUserDetail;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;

    public ProductResponseDto createProduct(CustomUserDetail user,ProductSaveRequestDto dto) {
        Product product = Product.of(dto.getProductCategory(), dto.getName(), dto.getContent(), dto.getPrice(), dto.getQuantity(),user.getUser());
        Product saveProduct = productRepository.save(product);

        return new ProductResponseDto(
            saveProduct.getId(),
            user.getUser().getId(),
            saveProduct.getCategory(),
            saveProduct.getName(),
            saveProduct.getContent(),
            saveProduct.getPrice(),
            saveProduct.getQuantity()
        );

    }

    public ProductResponseDto getProduct(Long productId) {

        Product findProduct = productRepository.getTodoByIdWithUser(productId)
            .orElseThrow(() -> new RuntimeException("재품이 존재하지 않습니다"));

        return new ProductResponseDto(
            findProduct.getId(),
            findProduct.getUser().getId(),
            findProduct.getCategory(),
            findProduct.getName(),
            findProduct.getContent(),
            findProduct.getPrice(),
            findProduct.getQuantity()
        );
   }

    public ProductResponseDto updateProduct(CustomUserDetail userDetail, Long productId, ProductUpdateRequestDto dto) {

        Product findProduct = productRepository.getTodoByIdWithUser(productId)
            .orElseThrow(() -> new RuntimeException("재품이 존재하지 않습니다"));

        if(userDetail.getUser().getId().equals(findProduct.getUser().getId())){
            findProduct.update(dto);
        } else {
            throw new RuntimeException("해당 제품의 생성자만 수정 가능합니다");
        }

        return new ProductResponseDto(
            findProduct.getId(),
            findProduct.getUser().getId(),
            findProduct.getCategory(),
            findProduct.getName(),
            findProduct.getContent(),
            findProduct.getPrice(),
            findProduct.getQuantity()
        );
    }

    public void deleteProduct(CustomUserDetail userDetail, Long productId) {

        Product findProduct = productRepository.getTodoByIdWithUser(productId)
            .orElseThrow(() -> new RuntimeException("재품이 존재하지 않습니다"));

        if(!(userDetail.getUser().getId().equals(findProduct.getUser().getId()))){
            throw new RuntimeException("해당 제품의 생성자만 삭제 가능합니다");
        }

        findProduct.updateDeleteStatus(Status.NON_EXIST.isValue());
        productRepository.save(findProduct);

    }
}
