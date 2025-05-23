package com.example.plusteamproject.domain.review;

import com.example.plusteamproject.domain.review.entity.SortType;
import com.example.plusteamproject.domain.review.service.ReviewService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

@SpringBootTest
public class ReviewServicePerformanceTest {

    @Autowired
    private ReviewService reviewService;

    @Test
    public void testPerformance() {
        Long productId = 1L;
        SortType sortType = SortType.SCORE_DESC;
        Pageable pageable = PageRequest.of(0, 50000);

        long start1 = System.currentTimeMillis();
        reviewService.getReviewsByProductId(productId, sortType, pageable);
        long time1 = System.currentTimeMillis() - start1;

        long start2 = System.currentTimeMillis();
        reviewService.getReviewsByProductIdWithIndex(productId, sortType, pageable);
        long time2 = System.currentTimeMillis() - start2;

        System.out.println("인덱스 X 쿼리 시간: " + time1 + " ms");
        System.out.println("인덱스 O 쿼리 시간: " + time2 + " ms");

        Assertions.assertTrue(time2 <= time1, "인덱스 활용 쿼리가 빠를 것으로 예상");
    }
}

