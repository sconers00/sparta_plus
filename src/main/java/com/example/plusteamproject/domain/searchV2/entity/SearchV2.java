package com.example.plusteamproject.domain.searchV2.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "searchs2")
@Getter
@NoArgsConstructor
public class SearchV2 {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String keyword;

    @Column(nullable = false)
    private Long count = 0L;

    public SearchV2(String keyword) {
        this.keyword = keyword;
        increaseCount();
    }

    public void increaseCount() {
        count++;
    }
}
