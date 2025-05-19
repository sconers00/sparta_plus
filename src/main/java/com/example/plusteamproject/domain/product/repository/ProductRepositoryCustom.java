package com.example.plusteamproject.domain.product.repository;

import com.example.plusteamproject.domain.product.entity.Product;

import java.util.Optional;

public interface ProductRepositoryCustom {

    Optional<Product> getTodoByIdWithUser(Long id);
}
