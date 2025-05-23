package com.example.plusteamproject.domain.review.controller;

import com.example.plusteamproject.common.ApiResponse;
import com.example.plusteamproject.domain.review.dto.request.DeleteReviewRequestDto;
import com.example.plusteamproject.domain.review.dto.request.ReviewRequestDto;
import com.example.plusteamproject.domain.review.dto.response.ReviewListResponseDto;
import com.example.plusteamproject.domain.review.dto.response.ReviewResponseDto;
import com.example.plusteamproject.domain.review.entity.SortType;
import com.example.plusteamproject.domain.review.service.ReviewService;
import com.example.plusteamproject.security.CustomUserDetail;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class ReviewController {

    private final ReviewService reviewService;

    @PostMapping("/orders/{orderId}/reviews")
    public ResponseEntity<ApiResponse<ReviewResponseDto>> saveReview(@PathVariable Long orderId, @Valid @RequestBody ReviewRequestDto dto, @AuthenticationPrincipal CustomUserDetail userDetail) {

        ReviewResponseDto savedReview = reviewService.saveReview(userDetail, orderId, dto);

        ApiResponse<ReviewResponseDto> apiResponse = new ApiResponse<>("리뷰 작성이 완료되었습니다.", savedReview);

        return new ResponseEntity<>(apiResponse, HttpStatus.CREATED);
    }

    // 기본 조회 (인덱싱 X, 캐싱 X)
    @GetMapping("/products/{productId}/reviews")
    public ResponseEntity<ApiResponse<ReviewListResponseDto>> getStoreReviews(@PathVariable Long productId, @RequestParam(defaultValue = "latest") String sort,
                                                                              @RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "50000") int size) {

        SortType sortType = SortType.from(sort);

        Pageable pageable = PageRequest.of(page, size);

        ReviewListResponseDto getReviewsList = reviewService.getReviewsByProductId(productId, sortType, pageable);

        ApiResponse<ReviewListResponseDto> apiResponse = new ApiResponse<>("리뷰 조회가 완료되었습니다.", getReviewsList);

        return new ResponseEntity<>(apiResponse, HttpStatus.OK);
    }

    // 기본 조회 (인덱싱 O, 캐싱 X)
    @GetMapping("/products/{productId}/reviews/indexing")
    public ResponseEntity<ApiResponse<ReviewListResponseDto>> getStoreReviewsIndex(@PathVariable Long productId, @RequestParam(defaultValue = "latest") String sort,
                                                                                   @RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "50000") int size) {

        SortType sortType = SortType.from(sort);

        Pageable pageable = PageRequest.of(page, size);

        ReviewListResponseDto getReviewsList = reviewService.getReviewsByProductIdWithIndex(productId, sortType, pageable);

        ApiResponse<ReviewListResponseDto> apiResponse = new ApiResponse<>("리뷰 조회가 완료되었습니다.", getReviewsList);

        return new ResponseEntity<>(apiResponse, HttpStatus.OK);
    }

    // 기본 조회 (인덱싱 O, 로컬 캐싱 O)
    @GetMapping("/products/{productId}/reviews/indexing-cashing")
    public ResponseEntity<ApiResponse<ReviewListResponseDto>> getStoreReviewsIndexAndCash(@PathVariable Long productId, @RequestParam(defaultValue = "latest") String sort,
                                                                                   @RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "500") int size) {

        SortType sortType = SortType.from(sort);

        Pageable pageable = PageRequest.of(page, size);

        ReviewListResponseDto getReviewsList = reviewService.getReviewsByProductIdWithIndexAndCash(productId, sortType, pageable);

        ApiResponse<ReviewListResponseDto> apiResponse = new ApiResponse<>("리뷰 조회가 완료되었습니다.", getReviewsList);

        return new ResponseEntity<>(apiResponse, HttpStatus.OK);
    }

    // 기본 조회 (인덱싱 O, Redis 캐싱 O)

    @PatchMapping("/reviews/{reviewId}")
    public ResponseEntity<ApiResponse<ReviewResponseDto>> updateReview(@PathVariable Long reviewId, @Valid @RequestBody ReviewRequestDto dto, @AuthenticationPrincipal CustomUserDetail userDetail) {

        ReviewResponseDto updatedReview = reviewService.updateReview(userDetail, reviewId, dto);

        ApiResponse<ReviewResponseDto> apiResponse = new ApiResponse<>("리뷰 수정이 완료되었습니다.", updatedReview);

        return new ResponseEntity<>(apiResponse, HttpStatus.OK);
    }

    @DeleteMapping("/reviews/{reviewId}")
    public ResponseEntity<ApiResponse<Void>> deleteReview(@PathVariable Long reviewId, @Valid @RequestBody DeleteReviewRequestDto dto, @AuthenticationPrincipal CustomUserDetail userDetail) {

        reviewService.deleteReview(userDetail, reviewId, dto);

        ApiResponse<Void> apiResponse = new ApiResponse<>("리뷰 삭제가 완료되었습니다.");

        return new ResponseEntity<>(apiResponse, HttpStatus.OK);
    }
}
