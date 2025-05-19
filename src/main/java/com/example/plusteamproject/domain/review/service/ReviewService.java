package com.example.plusteamproject.domain.review.service;

import com.example.plusteamproject.domain.review.dto.request.ReviewRequestDto;
import com.example.plusteamproject.domain.review.dto.response.ReviewResponseDto;
import com.example.plusteamproject.domain.review.entity.Review;
import com.example.plusteamproject.domain.review.repository.ReviewRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ReviewService {

    private final ReviewRepository reviewRepository;

    public ReviewResponseDto saveReview(Long tempUserId, Long tempOrderId, ReviewRequestDto dto) {
        
        // TODO: 주문 NOT FOUND 예외처리
        
        // TODO: 유저 NOT FOUND 예외처리
        
        // TODO: 본인의 주문인지 확인

        // TODO: 배송완료?

        // TODO: 배송 후 7일 이후 리뷰 작성 불가

        Long tempProductId = 1L;

        Review review = new Review(dto.getContent(), dto.getScore(), tempUserId, tempOrderId, tempProductId);

        Review saved = reviewRepository.save(review);

        return new ReviewResponseDto(saved);
    }
}
