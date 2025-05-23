package com.example.plusteamproject.domain.review.dto.response;

import com.example.plusteamproject.domain.order.entity.Order;
import com.example.plusteamproject.domain.review.entity.Review;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class ReviewResponseDto {

    // private final Long id;
    private Long id;

    // private final Long userId;
    private Long userId;

    // private final Long productId;
    private Long productId;

    // private final Long orderId;
    private Long orderId;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    // private final String content;
    private String content;

    // private final int score;
    private int score;

    public ReviewResponseDto(Review review) {
        this.id = review.getId();
        this.userId = review.getUserId();
        this.productId = review.getProductId();
        Order order = review.getOrder();
        this.orderId = order.getOrderId();
        this.content = review.getContent();
        this.score = review.getScore();
    }
}
