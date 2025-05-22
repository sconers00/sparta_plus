package com.example.plusteamproject.domain.product.dto;


import com.example.plusteamproject.domain.product.entity.Product;
import com.example.plusteamproject.domain.product.entity.ProductCategory;
import lombok.Getter;

import java.math.BigDecimal;

@Getter
public class ProductResponseDto {

    private final Long id;
    private final Long userId;
    private final ProductCategory productCategory;
    private final String name;
    private final String content;
    private final BigDecimal price;
    private final Long quantity;
    private final Long totalView;

    public ProductResponseDto(Product product, Long totalView) {
        this.id = product.getId();
        this.userId = product.getUser().getId();
        this.productCategory = product.getCategory();
        this.name = product.getName();
        this.content = product.getContent();
        this.price = product.getPrice();
        this.quantity = product.getQuantity();
        this.totalView = totalView;
    }

    public ProductResponseDto(Product product) {
        this.id = product.getId();
        this.userId = product.getUser().getId();
        this.productCategory = product.getCategory();
        this.name = product.getName();
        this.content = product.getContent();
        this.price = product.getPrice();
        this.quantity = product.getQuantity();
        this.totalView = product.getTotalView();
    }

    public ProductResponseDto(ProductRedisRequestDto dto, Long totalView) {
        this.id = dto.getId();
        this.userId = dto.getUserId();
        this.productCategory = dto.getCategory();
        this.name = dto.getName();
        this.content = dto.getContent();
        this.price = dto.getPrice();
        this.quantity = dto.getQuantity();
        this.totalView = totalView;
    }


}
