package com.example.plusteamproject.domain.review.controller;

import com.example.plusteamproject.common.ApiResponse;
import com.example.plusteamproject.domain.review.dto.request.ReviewRequestDto;
import com.example.plusteamproject.domain.review.dto.response.ReviewResponseDto;
import com.example.plusteamproject.domain.review.service.ReviewService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class ReviewController {

    private final ReviewService reviewService;

    @PostMapping("/orders/{orderId}/reviews")
    public ResponseEntity<ApiResponse<ReviewResponseDto>> saveReview(@PathVariable Long orderId, @Valid @RequestBody ReviewRequestDto dto) {

        // TODO: 인증인가 후 변경
        Long tempUserId = 1L;
        Long tempOrderId = 1L;


        ReviewResponseDto savedReview = reviewService.saveReview(tempUserId, tempOrderId, dto);

        ApiResponse<ReviewResponseDto> apiResponse = new ApiResponse<>("message", savedReview);

        return new ResponseEntity<>(apiResponse, HttpStatus.CREATED);
        // return ResponseEntity.status(HttpStatus.CREATED).body(ApiResponse("", savedReview))
    }
}
