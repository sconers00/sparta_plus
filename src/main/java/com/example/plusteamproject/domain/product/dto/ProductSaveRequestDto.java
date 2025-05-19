package com.example.plusteamproject.domain.product.dto;

import com.example.plusteamproject.domain.product.entity.ProductCategory;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;

import java.math.BigDecimal;

@Getter
public class ProductSaveRequestDto {

    @NotBlank
    private ProductCategory productCategory;


    @NotBlank(message = "이름을 입력해주세요.")
    private String name;

    private String content;

    @NotBlank(message = "가격을 입력해주세요.")
    private BigDecimal price;

    @NotBlank(message = "수량을 입력해주세요.")
    @Size(min = 1,message = "수량을 1개이상으로 입력해주세요.")
    private Long quantity;

}
