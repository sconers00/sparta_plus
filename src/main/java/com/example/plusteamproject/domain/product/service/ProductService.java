package com.example.plusteamproject.domain.product.service;

import com.example.plusteamproject.common.Status;
import com.example.plusteamproject.domain.product.dto.*;
import com.example.plusteamproject.domain.product.entity.Product;
import com.example.plusteamproject.domain.product.entity.ProductCategory;
import com.example.plusteamproject.domain.product.repository.ProductRepository;
import com.example.plusteamproject.security.CustomUserDetail;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Duration;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;
    private final RedisTemplate<String, Object> redisTemplate;
    private final ObjectMapper objectMapper;


    public ProductResponseDto createProduct(CustomUserDetail user, ProductSaveRequestDto dto) {
        Product product = Product.of(dto.getProductCategory(), dto.getName(), dto.getContent(), dto.getPrice(), dto.getQuantity(), user.getUser());
        Product saveProduct = productRepository.save(product);

        return new ProductResponseDto(saveProduct);

    }

    @Transactional
    public ProductResponseDto getProduct(Long productId) {

        Product findProduct = productRepository.getProductByIdWithUser(productId)
            .orElseThrow(() -> new RuntimeException("재품이 존재하지 않습니다"));

        findProduct.incrementTotalView();
        productRepository.save(findProduct);
        return new ProductResponseDto(findProduct);
    }


    public ProductResponseDto getProduct2(Long productId, CustomUserDetail userDetail) {
        String cacheKey = "product:" + productId;

        Object cachedObject = redisTemplate.opsForValue().get(cacheKey);

        if (cachedObject != null) {
            // LinkedHashMap -> ProductRedisRequestDto 로 변환
            ProductRedisRequestDto cachedDto = objectMapper.convertValue(cachedObject, ProductRedisRequestDto.class);
            increaseViewCount(productId, userDetail.getUser().getId());
            return new ProductResponseDto(cachedDto, getViewCount(productId));
        }

        Product findProduct = productRepository.getProductByIdWithUser(productId)
            .orElseThrow(() -> new RuntimeException("제품이 존재하지 않습니다"));

        redisTemplate.opsForValue().set(cacheKey, new ProductRedisRequestDto(findProduct), Duration.ofMinutes(10));
        increaseViewCount(productId, userDetail.getUser().getId());

        return new ProductResponseDto(findProduct, getViewCount(productId));
    }


    public Slice<ProductListResponseDto> findCursorProductBySizeAndCategory(ProductCategory category, Long lastId, int size) {

        Pageable pageable = PageRequest.of(0, size);

        return productRepository.findCursorProductBySizeAndCategory(category, lastId, pageable);
    }


    public ProductResponseDto updateProduct(CustomUserDetail userDetail, Long productId, ProductUpdateRequestDto dto) {

        Product findProduct = productRepository.getProductByIdWithUser(productId)
            .orElseThrow(() -> new RuntimeException("재품이 존재하지 않습니다"));

        if (userDetail.getUser().getId().equals(findProduct.getUser().getId())) {
            findProduct.update(dto);
            String cacheKey = "product:" + productId;
            redisTemplate.delete(cacheKey);
        } else {
            throw new RuntimeException("해당 제품의 생성자만 수정 가능합니다");
        }

        return new ProductResponseDto(findProduct, getViewCount(productId));
    }


    public void deleteProduct(CustomUserDetail userDetail, Long productId) {

        Product findProduct = productRepository.getProductByIdWithUser(productId)
            .orElseThrow(() -> new RuntimeException("재품이 존재하지 않습니다"));

        if (!(userDetail.getUser().getId().equals(findProduct.getUser().getId()))) {
            throw new RuntimeException("해당 제품의 생성자만 삭제 가능합니다");
        }


        findProduct.updateDeleteStatus(Status.NON_EXIST.isValue());
        productRepository.save(findProduct);

        String cacheKey = "product:" + productId;
        redisTemplate.delete(cacheKey);
    }


    public void increaseViewCount(Long productId, Long ipAddress) {
        String key = "viewId:" + productId;

        Boolean hasViewed = redisTemplate.opsForSet().isMember(key, ipAddress);
        if (Boolean.TRUE.equals(hasViewed)) {
            return;
        }
        redisTemplate.opsForZSet().incrementScore("viewCount", productId.toString(), 1);

        // 조회 기록 추가 및 TTL 설정
        redisTemplate.opsForSet().add(key, ipAddress);
    }

    public Long getViewCount(Long id) {
        Double score = redisTemplate.opsForZSet().score("viewCount", id.toString());
        return score != null ? score.longValue() : 0L;
    }

}
