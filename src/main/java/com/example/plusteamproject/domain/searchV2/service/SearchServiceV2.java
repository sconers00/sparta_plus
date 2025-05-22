package com.example.plusteamproject.domain.searchV2.service;

import com.example.plusteamproject.domain.product.dto.ProductResponseDto;
import com.example.plusteamproject.domain.product.entity.Product;
import com.example.plusteamproject.domain.product.repository.ProductRepository;
import com.example.plusteamproject.domain.searchV1.dto.PopularSearchResponseDto;
import com.example.plusteamproject.domain.searchV2.entity.SearchV2;
import com.example.plusteamproject.domain.searchV2.repository.SearchRepositoryV2;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.IntStream;

@Service
@RequiredArgsConstructor
public class SearchServiceV2 {

    private final SearchRepositoryV2 searchRepository;
    private final ProductRepository productRepository;

    @Transactional
    public void increaseSearchCount(String keyword) {

        SearchV2 search = searchRepository.findByKeyword(keyword)
                .orElseGet(() -> searchRepository.save(new SearchV2(keyword)));

        search.increaseCount();
    }

    @Cacheable(value = "searchCache", key = "#keyword")
    public Slice<ProductResponseDto> findByProductName(String keyword) {

        Pageable pageable = PageRequest.of(0,10);
        Slice<Product> products = productRepository.findByProductNameContaining(keyword, pageable);

        return products.map(ProductResponseDto::new);
    }

    @Transactional(readOnly = true)
    public List<PopularSearchResponseDto> findByPopulation() {

        List<SearchV2> top10 = searchRepository.findTop10ByOrderByCountDesc();

        return IntStream.range(0, top10.size())
                .mapToObj(i -> new PopularSearchResponseDto(i + 1, top10.get(i).getKeyword()))
                .toList();
    }

}
