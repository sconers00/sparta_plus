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

    public ProductResponseDto(Product product) {
        this.id = product.getId();
        this.userId = product.getUser().getId();
        this.productCategory = product.getCategory();
        this.name = product.getName();
        this.content = product.getContent();
        this.price = product.getPrice();
        this.quantity = product.getQuantity();
    }
}
