package com.example.plusteamproject.domain.product.repository;

import com.example.plusteamproject.domain.product.entity.Product;
import com.example.plusteamproject.domain.product.entity.QProduct;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class ProductRepositoryImpl implements ProductRepositoryCustom{

   private final JPAQueryFactory jpaQueryFactory;

    @Override
    public Optional<Product> getTodoByIdWithUser(Long id) {
        QProduct product = QProduct.product;

        return Optional.ofNullable(jpaQueryFactory
            .selectFrom(product)
            .where(product.id.eq(id))
            .fetchOne()
        );
    }
}
