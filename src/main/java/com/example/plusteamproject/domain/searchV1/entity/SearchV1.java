package com.example.plusteamproject.domain.searchV1.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@Table(name = "searchs1")
@NoArgsConstructor
public class SearchV1 {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String keyword;

    @Column(nullable = false)
    private Long count = 0L;

    public SearchV1(String keyword) {
        this.keyword = keyword;
        increaseCount();
    }

    public void increaseCount() {
        count++;
    }
}
