package com.example.plusteamproject.dummy;

import com.example.plusteamproject.domain.order.entity.Order;
import com.example.plusteamproject.domain.order.repository.OrderRepository;
import com.example.plusteamproject.domain.review.entity.Review;
import com.example.plusteamproject.domain.review.repository.ReviewRepository;
import lombok.AllArgsConstructor;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
/*
    TODO: user, product, order 최소 1개씩 만든 후 주석 해제 하고 재실행
 */
// @Component
// @AllArgsConstructor
// public class DummyReviewWithOrderGenerator implements ApplicationRunner {
//
//     private final OrderRepository orderRepository;
//     private final ReviewRepository reviewRepository;
//
//     @Override
//     @Transactional
//     public void run(ApplicationArguments args) {
//
//         long count = reviewRepository.count();
//         if (count >= 200000) {
//             System.out.println("리뷰가 이미 충분히 존재해서 생성하지 않습니다.");
//             return;
//         }
//
//         int total = 200000;
//         int batchSize = 1000;
//         List<Review> batch = new ArrayList<>();
//         Random random = new Random();
//
//         Order order = new Order();
//         order.setOrderId(1L);
//
//         for (int i = 1; i <= total; i++) {
//             Review review = new Review();
//             review.setContent("리뷰 내용 " + i);
//             review.setScore(random.nextInt(5) + 1);
//             review.setProductId(1L);
//             review.setUserId(1L);
//             review.setOrder(order);
//
//             batch.add(review);
//
//             if (i % batchSize == 0) {
//                 reviewRepository.saveAll(batch);
//                 batch.clear();
//                 System.out.println(i + "개 리뷰 저장 완료");
//             }
//         }
//
//         if (!batch.isEmpty()) {
//             reviewRepository.saveAll(batch);
//         }
//
//         System.out.println("== 리뷰 생성 완료 ==");
//     }
// }
