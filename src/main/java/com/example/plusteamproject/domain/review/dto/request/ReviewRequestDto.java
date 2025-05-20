package com.example.plusteamproject.domain.review.dto.request;

import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.hibernate.validator.constraints.Range;

@Getter
@AllArgsConstructor
public class ReviewRequestDto {

    @Size(max = 100, message = "리뷰 내용은 100글자 이하로 작성해야 합니다.")
    private final String content;

    @Range(min = 1, max = 5)
    private final int score;
}
