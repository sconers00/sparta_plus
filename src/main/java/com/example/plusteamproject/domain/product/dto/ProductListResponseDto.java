package com.example.plusteamproject.domain.product.dto;

import com.example.plusteamproject.domain.product.entity.Product;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.math.BigDecimal;

@Getter
@AllArgsConstructor
public class ProductListResponseDto {

    private final String name;

    private final BigDecimal price;

    public ProductListResponseDto(Product product) {
        this.name = product.getName();
        this.price = product.getPrice();

    }


}
