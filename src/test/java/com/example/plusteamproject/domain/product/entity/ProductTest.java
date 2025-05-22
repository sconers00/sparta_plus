package com.example.plusteamproject.domain.product.entity;

import com.example.plusteamproject.common.Status;
import com.example.plusteamproject.domain.product.dto.ProductUpdateRequestDto;
import com.example.plusteamproject.domain.user.dto.request.CreateUserRequestDto;
import com.example.plusteamproject.domain.user.entity.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ProductTest {

    Product product;
    User user;

    @BeforeEach
    void setUp() {
        CreateUserRequestDto dto = new CreateUserRequestDto("test@example.com", "Test User", "testnick", "test123123", "010-1234-5678", "ADMIN");
        user = new User(dto, "test123123");
        product = new Product(1L, ProductCategory.BOOKS, "도라에몽", "내용", new BigDecimal(7000), (long) 1000, 0L, user, Status.EXIST.isValue());
    }

    @Test
    void 일부정보_업데이트() {
        ProductUpdateRequestDto dto = new ProductUpdateRequestDto(
            null, null, null, new BigDecimal(9000), null);

        product.update(dto);

        assertEquals(ProductCategory.BOOKS, product.getCategory());
        assertEquals("도라에몽", product.getName());
        assertEquals("내용", product.getContent());
        assertEquals(new BigDecimal("9000"), product.getPrice());
        assertEquals(1000L, product.getQuantity());
        assertEquals(user, product.getUser());
        assertEquals(Status.EXIST.isValue(), product.isDeleted());
    }


}
