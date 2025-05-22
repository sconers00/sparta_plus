package com.example.plusteamproject.domain.product.entity;

import lombok.Getter;

@Getter
public enum ProductCategory {
    FASHION("패션"),
    ELECTRONICS("전자제품"),
    BEAUTY("뷰티"),
    SPORTS("스포츠"),
    BOOKS("도서"),
    FOOD("식품"),
    FURNITURE("가구"),
    TOYS("장난감"),
    PET_SUPPLIES("반려동물"),
    AUTOMOTIVE("자동차"),
    HEALTH("건강"),
    OFFICE_SUPPLIES("사무용품");

    private final String displayName;

    ProductCategory(String displayName) {
        this.displayName = displayName;
    }
}
