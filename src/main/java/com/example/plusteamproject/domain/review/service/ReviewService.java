package com.example.plusteamproject.domain.review.service;

import com.example.plusteamproject.domain.order.entity.Order;
import com.example.plusteamproject.domain.order.entity.OrderStatus;
import com.example.plusteamproject.domain.order.repository.OrderRepository;
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
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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
    private final OrderRepository orderRepository;
    private final PasswordEncoder passwordEncoder;

    public ReviewResponseDto saveReview(CustomUserDetail userDetail, Long orderId, ReviewRequestDto dto) {

        User user = userDetail.getUser();

        Order order = getOrderByOrderId(orderId);

        validMyOrder(order.getUserId().getId(), user.getId());

        if (order.getOrderStatus().equals(OrderStatus.ARRIVED)) {
            throw new RuntimeException("배송이 완료되지 않아 현재 리뷰 작성이 불가능 합니다.");
        }

        Long tempProductId = 1L;

        Review review = new Review(dto.getContent(), dto.getScore(), user.getId(), order, tempProductId);

        Review saved = reviewRepository.save(review);

        return new ReviewResponseDto(saved);
    }

    public ReviewListResponseDto getReviewsByProductId(Long productId, SortType sortType, Pageable pageable) {

        Page<Review> page = reviewRepository.findByProductId(productId, pageable);

        Stream<Review> stream = page.getContent().stream();

        if (SORT_COMPARATORS.containsKey(sortType)) {
            stream = stream.sorted(SORT_COMPARATORS.get(sortType));
        }

        List<ReviewResponseDto> reviewList = stream.map(this::convertToDto).toList();

        Long count = page.getTotalElements();
        double average = reviewList.stream().mapToInt(ReviewResponseDto::getScore).average().orElse(0.0);

        return new ReviewListResponseDto(count, average, reviewList);
    }

    public ReviewListResponseDto getReviewsByProductIdWithIndex(Long productId, SortType sortType, Pageable pageable) {

        Page<Review> page = switch (sortType) {
            case SCORE_DESC -> reviewRepository.findByProductIdOrderByScoreDesc(productId, pageable);
            case SCORE_ASC -> reviewRepository.findByProductIdOrderByScoreAsc(productId, pageable);
            default -> reviewRepository.findByProductIdOrderByCreatedAtDesc(productId, pageable);
        };

        List<ReviewResponseDto> reviewList = page.getContent()
                .stream()
                .map(this::convertToDto)
                .toList();

        Long count = page.getTotalElements();
        double average = reviewList.stream()
                .mapToInt(ReviewResponseDto::getScore)
                .average()
                .orElse(0.0);

        return new ReviewListResponseDto(count, average, reviewList);
    }

    @Cacheable(
            value = "productReviewCache",
            key = "#productId + '_' + #sortType.name() + '_' + #pageable.pageNumber + '_' + #pageable.pageSize",
            condition = "#pageable.pageNumber == 0 && #pageable.pageSize == 500"
    )
    public ReviewListResponseDto getReviewsByProductIdWithIndexAndCash(Long productId, SortType sortType, Pageable pageable) {
        System.out.println("캐시 미스 - DB에서 조회");
        Page<Review> page = switch (sortType) {
            case SCORE_DESC -> reviewRepository.findByProductIdOrderByScoreDesc(productId, pageable);
            case SCORE_ASC -> reviewRepository.findByProductIdOrderByScoreAsc(productId, pageable);
            default -> reviewRepository.findByProductIdOrderByCreatedAtDesc(productId, pageable);
        };

        List<ReviewResponseDto> reviewList = page.getContent()
                .stream()
                .map(this::convertToDto)
                .toList();

        Long count = page.getTotalElements();
        double average = reviewList.stream()
                .mapToInt(ReviewResponseDto::getScore)
                .average()
                .orElse(0.0);

        return new ReviewListResponseDto(count, average, reviewList);
    }

    @Transactional
    public ReviewResponseDto updateReview(CustomUserDetail userDetail, Long reviewId, ReviewRequestDto dto) {

        User user = userDetail.getUser();

        // 해당 리뷰가 없으면 예외처리
        Review review = getReviewByReviewId(reviewId);

        // 본인의 리뷰인지 확인
        validMyReview(reviewId, user.getId());

        review.updateReview(dto.getContent(), dto.getScore());

        return new ReviewResponseDto(review);
    }

    public void deleteReview(CustomUserDetail userDetail, Long reviewId, DeleteReviewRequestDto dto) {

        User user = userDetail.getUser();

        // 해당 리뷰가 없으면 예외처리
        Review review = getReviewByReviewId(reviewId);

        // 본인의 리뷰인지 확인
        validMyReview(reviewId, user.getId());

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

    public Order getOrderByOrderId(Long orderId) {
        return orderRepository.findById(orderId).orElseThrow(() -> new RuntimeException("해당 주문이 존재하지 않습니다."));
    }

    public void validMyOrder(Long orderId, Long userId) {
        if (!orderId.equals(userId)) {
            throw new RuntimeException("해당 주문이 본인의 주문이 아닙니다.");
        }
    }

    public void validMyReview(Long reviewId, Long userId) {
        if (!reviewId.equals(userId)) {
            throw new RuntimeException("본인이 작성한 리뷰가 아닙니다.");
        }
    }
}
