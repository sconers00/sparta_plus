package com.example.plusteamproject.domain.product.dto;

import com.example.plusteamproject.domain.product.entity.Product;
import com.example.plusteamproject.domain.product.entity.ProductCategory;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
public class ProductRedisRequestDto implements Serializable {


    private Long id;
    private ProductCategory category;
    private String name;
    private String content;
    private BigDecimal price;
    private Long quantity;
    private Long userId;
    private Long totalView;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    @JsonProperty("deleted")
    private boolean isDeleted;

    public ProductRedisRequestDto(Product product) {
        this.id = product.getId();
        this.category = product.getCategory();
        this.name = product.getName();
        this.content = product.getContent();
        this.price = product.getPrice();
        this.quantity = product.getQuantity();
        this.userId = product.getUser().getId();
        this.totalView = product.getTotalView();
        this.createdAt = product.getCreatedAt();
        this.updatedAt = product.getUpdatedAt();
        this.isDeleted = product.isDeleted();
    }

}
