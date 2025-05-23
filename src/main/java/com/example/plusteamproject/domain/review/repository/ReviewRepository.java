package com.example.plusteamproject.domain.review.repository;

import com.example.plusteamproject.domain.review.entity.Review;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ReviewRepository extends JpaRepository<Review, Long> {

    Page<Review> findByProductId(Long productId, Pageable pageable);

    Page<Review> findByProductIdOrderByScoreDesc(Long productId, Pageable pageable);

    Page<Review> findByProductIdOrderByScoreAsc(Long productId, Pageable pageable);

    Page<Review> findByProductIdOrderByCreatedAtDesc(Long productId, Pageable pageable);

    @Query("SELECT AVG(r.score) FROM Review r WHERE r.productId = :productId")
    Double findAverageScoreByProductId(@Param("productId") Long productId);


}
