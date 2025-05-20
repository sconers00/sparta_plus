package com.example.plusteamproject.domain.product.service;

import com.example.plusteamproject.common.Status;
import com.example.plusteamproject.domain.product.dto.ProductResponseDto;
import com.example.plusteamproject.domain.product.entity.Product;
import com.example.plusteamproject.domain.product.entity.ProductCategory;
import com.example.plusteamproject.domain.product.repository.ProductRepositoryImpl;
import com.example.plusteamproject.domain.user.dto.request.CreateUserRequestDto;
import com.example.plusteamproject.domain.user.entity.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;


@ExtendWith(MockitoExtension.class)
class ProductServiceTest {

    @Mock
    private ProductRepositoryImpl productRepositoryImpl;

    @InjectMocks
    private ProductService productService;

    Product product;

    @BeforeEach
    void setUp() {
        CreateUserRequestDto dto = new CreateUserRequestDto("test@example.com", "Test User", "testnick", "test123123", "010-1234-5678", "ADMIN");
        User user = new User(dto, "test123123");
        product = new Product(1L, ProductCategory.BOOKS, "도라에몽", "내용", new BigDecimal(7000), (long) 1000, user, Status.EXIST.isValue());
    }

    @Test
    void 제품_상세조회성공() {
        given(productRepositoryImpl.getTodoByIdWithUser(1L)).willReturn(Optional.of(product));

        ProductResponseDto result = productService.getProduct(1L);

        assertThat(result).isNotNull();
        assertThat(result).isEqualTo(product);
    }

    @Test
    void findCursorProductBySizeAndCategory() {
    }

    @Test
    void updateProduct() {
    }

    @Test
    void deleteProduct() {
    }
}
