package com.example.plusteamproject.domain.review.repository;

import com.example.plusteamproject.domain.review.entity.Review;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReviewRepository extends JpaRepository<Review, Long> {
}
