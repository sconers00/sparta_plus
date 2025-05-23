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
@Table(name = "review", indexes = {@Index(name = "idx_score", columnList = "score")})
public class Review extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String content;

    private int score;

    private Long userId;

    // @OneToOne(fetch = FetchType.LAZY)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id")
    private Order order;

    private Long productId;

    public Review(String content, int score, Long userId, Order order, Long productId) {
        this.content = content;
        this.score = score;
        this.userId = userId;
        this.order = order;
        this.productId = productId;
    }

    public void updateReview(String content, int score) {
        this.content = content;
        this.score = score;
    }
}
