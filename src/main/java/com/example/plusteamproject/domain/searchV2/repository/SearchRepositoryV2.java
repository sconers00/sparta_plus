package com.example.plusteamproject.domain.searchV2.repository;

import com.example.plusteamproject.domain.searchV2.entity.SearchV2;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface SearchRepositoryV2 extends JpaRepository<SearchV2, Long> {
    Optional<SearchV2> findByKeyword(String keyword);

    List<SearchV2> findTop10ByOrderByCountDesc();
}
