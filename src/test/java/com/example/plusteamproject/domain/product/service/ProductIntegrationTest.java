package com.example.plusteamproject.domain.product.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest
@ActiveProfiles("test")
public class ProductIntegrationTest {

    @Autowired
    private ProductService productService;


    @Test
    public void getProduct() {

        // Given: 1000번 호출로 성능 테스트
        int iterations = 1000;
        long startTime = System.nanoTime();

        // When: getProduct 호출 (DB 조회수 증가)
        for (int i = 0; i < iterations; i++) {
            productService.getProduct(7000L);
        }

        // Then: 실행 시간 출력
        long endTime = System.nanoTime();
        double durationMs = (endTime - startTime) / 1_000_000.0;
        System.out.println("getProduct (DB) took: " + durationMs + " ms for " + iterations + " calls");


    }

    @Test
    void testGetProduct2() {
        // Given: 1000번 호출로 성능 테스트
        int iterations = 1000;
        long startTime = System.nanoTime();

        // When: getProduct2 호출 (Redis 조회수 증가)
        for (int i = 0; i < iterations; i++) {
            productService.getProduct2(7000L, null);
        }

        // Then: 실행 시간 출력
        long endTime = System.nanoTime();
        double durationMs = (endTime - startTime) / 1_000_000.0;
        System.out.println("getProduct2 (Redis) took: " + durationMs + " ms for " + iterations + " calls");

    }
}
