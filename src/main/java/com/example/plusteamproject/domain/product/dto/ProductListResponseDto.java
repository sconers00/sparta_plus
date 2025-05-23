package com.example.plusteamproject.domain.product.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.math.BigDecimal;

@Getter
@AllArgsConstructor
public class ProductListResponseDto {

    private final String name;

    private final BigDecimal price;

    
}
