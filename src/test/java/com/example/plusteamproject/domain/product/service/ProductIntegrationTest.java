package com.example.plusteamproject.domain.product.service;

import com.example.plusteamproject.domain.product.entity.Product;
import com.example.plusteamproject.domain.product.entity.ProductCategory;
import com.example.plusteamproject.domain.product.repository.ProductRepository;
import com.example.plusteamproject.domain.user.dto.request.CreateUserRequestDto;
import com.example.plusteamproject.domain.user.entity.User;
import com.example.plusteamproject.domain.user.repository.UserRepository;
import com.example.plusteamproject.security.CustomUserDetail;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@SpringBootTest
@ActiveProfiles("test")
public class ProductIntegrationTest {

    @Autowired
    private ProductService productService;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private UserRepository userRepository;
    User testUser;

    @BeforeEach
    public void init() {
        CreateUserRequestDto userRequest = new CreateUserRequestDto(
            "test@example.com",
            "홍길동",
            "테스트",
            "password123",
            "01012345678",
            "USER"
        );
        testUser = new User(userRequest, "password");
        userRepository.save(testUser);

        List<Product> products = new ArrayList<>();
        for (int i = 1; i <= 10_000; i++) {
            Product product = Product.of(
                ProductCategory.FOOD,
                "Product " + i,
                null,
                BigDecimal.valueOf(1000.0 + (i % 100) * 100),
                (long) (100 - (i % 50)),
                testUser
            );
            products.add(product);
        }
        productRepository.saveAll(products);
    }

    @Test
    public void 조회수_성능테스트() {
        int iterations = 1000;
        long startTimeGetProduct = System.nanoTime();
        for (int i = 0; i < iterations; i++) {
            productService.getProduct(7000L);
        }
        long endTimeGetProduct = System.nanoTime();
        double durationMsGetProduct = (endTimeGetProduct - startTimeGetProduct) / 1_000_000.0;

        long startTimeGetProduct2 = System.nanoTime();
        for (int i = 0; i < iterations; i++) {
            CreateUserRequestDto userRequest = new CreateUserRequestDto(
                "test@example.com",
                "홍길동" + i,
                "테스트",
                "password12" + i,
                null,
                "USER"
            );
            User user1 = new User(userRequest, "password12" + i);
            CustomUserDetail customDetail = new CustomUserDetail(user1);
            productService.getProduct2(7000L, customDetail);
        }
        long endTimeGetProduct2 = System.nanoTime();
        double durationMsGetProduct2 = (endTimeGetProduct2 - startTimeGetProduct2) / 1_000_000.0;


        System.out.println("=== Performance Test Results ===");
        System.out.printf("DB에 조회수저장 시간: %.2f ms for %d calls%n", durationMsGetProduct, iterations);
        System.out.printf("Redis에 조회수 저장 시간 : %.2f ms for %d calls%n", durationMsGetProduct2, iterations);
    }
}
