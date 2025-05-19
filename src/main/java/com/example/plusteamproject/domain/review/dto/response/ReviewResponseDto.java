package com.example.plusteamproject.domain.review.dto.response;

import com.example.plusteamproject.domain.review.entity.Review;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ReviewResponseDto {

    private final Long id;

    private final Long userId;

    private final Long productId;

    private final Long orderId;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private final String content;

    private final int score;

    public ReviewResponseDto(Review review) {
        this.id = review.getId();
        this.userId = review.getUserId();
        this.productId = review.getProductId();
        this.orderId = review.getOrderId();
        this.content = review.getContent();
        this.score = review.getScore();
    }
}
