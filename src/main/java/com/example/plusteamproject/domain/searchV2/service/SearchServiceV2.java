package com.example.plusteamproject.domain.searchV2.service;

import com.example.plusteamproject.domain.product.dto.ProductResponseDto;
import com.example.plusteamproject.domain.product.entity.Product;
import com.example.plusteamproject.domain.product.repository.ProductRepository;
import com.example.plusteamproject.domain.searchV1.dto.PopularSearchResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ZSetOperations;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Duration;
import java.util.List;
import java.util.Set;
import java.util.stream.IntStream;

@Service
@RequiredArgsConstructor
public class SearchServiceV2 {

    private final RedisTemplate<String, Object> redisTemplate;
    private final ProductRepository productRepository;

    @Transactional
    public Slice<ProductResponseDto> findByProductName(String keyword) {

        Pageable pageable = PageRequest.of(0,10);
        Slice<Product> products = productRepository.findByProductNameContaining(keyword, pageable);

        increaseSearchCount(keyword);

        return products.map(ProductResponseDto::new);
    }

    @Transactional(readOnly = true)
    public List<PopularSearchResponseDto> findByPopulation() {

        Set<ZSetOperations.TypedTuple<Object>> top10 = redisTemplate.opsForZSet().reverseRangeWithScores("search:ranking", 0, 9);

        List<ZSetOperations.TypedTuple<Object>> top10List = top10.stream().toList();

        return IntStream.range(0, top10List.size())
                .mapToObj(i -> new PopularSearchResponseDto(i + 1, (String) top10List.get(i).getValue()))
                .toList();
    }

    public void increaseSearchCount(String keyword) {

        redisTemplate.opsForZSet().incrementScore("search:ranking", keyword, 1);

        redisTemplate.expire("search:ranking", Duration.ofHours(1));
    }

}
