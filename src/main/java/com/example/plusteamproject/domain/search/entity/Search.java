package com.example.plusteamproject.domain.search.entity;

import com.example.plusteamproject.domain.product.entity.Product;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@Entity
@Table(name = "searchs")
@NoArgsConstructor
public class Search {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToMany
    @JoinColumn(name = "products")
    private List<Product> products;

    @Column(nullable = false)
    private Long count = 0L;
}
