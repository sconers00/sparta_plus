package com.example.plusteamproject.domain.review.service;

import com.example.plusteamproject.domain.review.dto.request.DeleteReviewRequestDto;
import com.example.plusteamproject.domain.review.dto.request.ReviewRequestDto;
import com.example.plusteamproject.domain.review.dto.response.ReviewListResponseDto;
import com.example.plusteamproject.domain.review.dto.response.ReviewResponseDto;
import com.example.plusteamproject.domain.review.entity.Review;
import com.example.plusteamproject.domain.review.entity.SortType;
import com.example.plusteamproject.domain.review.repository.ReviewRepository;
import com.example.plusteamproject.domain.user.entity.User;
import com.example.plusteamproject.domain.user.repository.UserRepository;
import com.example.plusteamproject.security.CustomUserDetail;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

@Service
@RequiredArgsConstructor
public class ReviewService {

    private final UserRepository userRepository;
    private final ReviewRepository reviewRepository;
    private final PasswordEncoder passwordEncoder;

    public ReviewResponseDto saveReview(CustomUserDetail userDetail, Long tempOrderId, ReviewRequestDto dto) {

        User user = userDetail.getUser();

        // TODO: 주문 NOT FOUND 예외처리

        // TODO: 본인의 주문인지 확인

        // TODO: 배송완료?

        // TODO: 배송 후 7일 이후 리뷰 작성 불가

        Long tempProductId = 1L;

        Review review = new Review(dto.getContent(), dto.getScore(), user.getId(), tempOrderId, tempProductId);

        Review saved = reviewRepository.save(review);

        return new ReviewResponseDto(saved);
    }

    public ReviewListResponseDto getReviewsByProductId(Long productId, SortType sortType) {

        List<Review> reviews = reviewRepository.findByProductId(productId);

        Stream<Review> stream = reviews.stream();

        if (SORT_COMPARATORS.containsKey(sortType)) {
            stream = stream.sorted(SORT_COMPARATORS.get(sortType));
        }

        List<ReviewResponseDto> reviewList = stream.map(this::convertToDto).toList();

        Long count = (long) reviewList.size();
        double average = reviewList.stream().mapToInt(ReviewResponseDto::getScore).average().orElse(0.0);

        return new ReviewListResponseDto(count, average, reviewList);
    }

    @Transactional
    public ReviewResponseDto updateReview(CustomUserDetail userDetail, Long reviewId, ReviewRequestDto dto) {

        User user = userDetail.getUser();

        // 해당 리뷰가 없으면 예외처리
        Review review = getReviewByReviewId(reviewId);

        // TODO: 본인의 리뷰인지 확인

        // TODO: 배송 후 7일 이후 리뷰 수정 불가

        review.updateReview(dto.getContent(), dto.getScore());

        return new ReviewResponseDto(review);
    }

    public void deleteReview(CustomUserDetail userDetail, Long reviewId, DeleteReviewRequestDto dto) {

        User user = userDetail.getUser();

        // 해당 리뷰가 없으면 예외처리
        Review review = getReviewByReviewId(reviewId);

        // TODO: 본인의 리뷰인지 확인

        validMatchingPassword(dto.getPassword(), user.getPassword());

        reviewRepository.delete(review);
    }
    
    public void validMatchingPassword(String inputPassword, String password) {
        if (!passwordEncoder.matches(inputPassword, password)) {
            throw new RuntimeException("비밀번호가 일치하지 않습니다.");
        }
    }

    private ReviewResponseDto convertToDto(Review review) {
        return new ReviewResponseDto(review);
    }

    private final Map<SortType, Comparator<Review>> SORT_COMPARATORS = Map.of(
            SortType.SCORE_DESC, Comparator.comparingInt(Review::getScore).reversed(),
            SortType.SCORE_ASC, Comparator.comparingInt(Review::getScore),
            SortType.LATEST, Comparator.comparing(Review::getCreatedAt).reversed()
    );

    public Review getReviewByReviewId(Long reviewId) {
        return reviewRepository.findById(reviewId).orElseThrow(() -> new RuntimeException("해당 리뷰가 존재하지 않습니다."));
    }
}
