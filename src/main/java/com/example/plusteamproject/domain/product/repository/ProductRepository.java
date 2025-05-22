package com.example.plusteamproject.domain.product.repository;

import com.example.plusteamproject.domain.product.entity.Product;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import org.springframework.data.domain.Pageable;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long>, ProductRepositoryCustom {

    @Query("SELECT p FROM Product p WHERE p.name LIKE %:keyword%")
    Slice<Product> findByProductNameContaining(@Param("keyword") String keyword, Pageable pageable);

}
