package com.example.plusteamproject.domain.search.repository;

import com.example.plusteamproject.domain.search.entity.Search;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface SearchRepository extends JpaRepository<Search, Long> {
    Optional<Search> findByKeyword(String keyword);

    List<Search> findTop10ByOrderByCountDesc();
}
