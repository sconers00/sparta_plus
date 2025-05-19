package com.example.plusteamproject.domain.review.entity;

import com.example.plusteamproject.common.BaseEntity;
import com.example.plusteamproject.domain.order.entity.Order;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@Entity
@Table(name = "review")
public class Review extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String content;

    private int score;

    private Long userId;

    // TODO: Order 추가 시 설정
    // @OneToOne(fetch = FetchType.LAZY)
    // @JoinColumn(name = "order_id")
    // private Order order;
    private Long orderId;

    private Long productId;

    // TODO: 추후 변경
    public Review(String content, int score, Long userId, Long orderId, Long productId) {
        this.content = content;
        this.score = score;
        this.userId = userId;
        this.orderId = orderId;
        this.productId = productId;
    }

    public void updateReview(String content, int score) {
        this.content = content;
        this.score = score;
    }
}
