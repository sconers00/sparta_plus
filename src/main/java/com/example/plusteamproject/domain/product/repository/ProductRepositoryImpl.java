package com.example.plusteamproject.domain.product.repository;

import com.example.plusteamproject.domain.product.dto.ProductListResponseDto;
import com.example.plusteamproject.domain.product.entity.Product;
import com.example.plusteamproject.domain.product.entity.ProductCategory;
import com.example.plusteamproject.domain.product.entity.QProduct;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.support.PageableExecutionUtils;
import org.springframework.stereotype.Repository;

import java.util.List;
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

    @Override
    public Slice<ProductListResponseDto> findCursorProductBySizeAndCategory(ProductCategory category, Long lastId, Pageable pageable) {

        QProduct product =QProduct.product;

        List<ProductListResponseDto> content = jpaQueryFactory
            .select(Projections.constructor(ProductListResponseDto.class,product.name,product.price))
            .from(product)
            .where(product.category.eq(category))
            .orderBy(product.createdAt.desc())
            .offset(pageable.getOffset())
            .limit(pageable.getPageSize())
            .fetch();

        JPAQuery<Long> totalCountQuery = jpaQueryFactory
            .select(product.count())
            .from(product)
            .where(product.category.eq(category));

        return PageableExecutionUtils.getPage(content,pageable,()->totalCountQuery.fetch().size());
    }
}
