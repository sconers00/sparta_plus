package com.example.plusteamproject.domain.review.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class ReviewListResponseDto {

    // private final Long countReviews;
    private Long countReviews;

    // private final double averageScore;
    private double averageScore;

    // private final List<ReviewResponseDto> reviewsList;
    private List<ReviewResponseDto> reviewsList;
}
