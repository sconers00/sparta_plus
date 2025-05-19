package com.example.plusteamproject.domain.product.dto;

import com.example.plusteamproject.domain.product.entity.ProductCategory;
import lombok.Getter;

import java.math.BigDecimal;

@Getter
public class ProductUpdateRequestDto {

    private ProductCategory productCategory;
    private String name;
    private String content;
    private BigDecimal price;
    private Long quantity;

}
