package com.example.plusteamproject.domain.searchV1.repository;

import com.example.plusteamproject.domain.searchV1.entity.SearchV1;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface SearchRepositoryV1 extends JpaRepository<SearchV1, Long> {

    @Query("SELECT s FROM SearchV1 s WHERE s.keyword = :keyword")
    Optional<SearchV1> findByKeyword(@Param("keyword") String keyword);

    List<SearchV1> findTop10ByOrderByCountDesc();
}
