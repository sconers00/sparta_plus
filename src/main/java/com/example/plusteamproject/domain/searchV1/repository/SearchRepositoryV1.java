package com.example.plusteamproject.domain.searchV1.repository;

import com.example.plusteamproject.domain.searchV1.entity.SearchV1;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface SearchRepositoryV1 extends JpaRepository<SearchV1, Long> {
    Optional<SearchV1> findByKeyword(String keyword);

    List<SearchV1> findTop10ByOrderByCountDesc();
}
