package com.example.plusteamproject.domain.searchV1.service;

import com.example.plusteamproject.domain.product.dto.ProductResponseDto;
import com.example.plusteamproject.domain.product.entity.Product;
import com.example.plusteamproject.domain.product.repository.ProductRepository;
import com.example.plusteamproject.domain.searchV1.dto.PopularSearchResponseDto;
import com.example.plusteamproject.domain.searchV1.entity.SearchV1;
import com.example.plusteamproject.domain.searchV1.repository.SearchRepositoryV1;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.IntStream;

@Service
@RequiredArgsConstructor
public class SearchServiceV1 {

    private final SearchRepositoryV1 searchRepository;
    private final ProductRepository productRepository;

    @Transactional
    public Slice<ProductResponseDto> findByProductName(String keyword) {

        SearchV1 search = searchRepository.findByKeyword(keyword).orElseGet(() -> searchRepository.save(new SearchV1(keyword)));
        search.increaseCount();

        Pageable pageable = PageRequest.of(0,10);

        Slice<Product> products = productRepository.findByProductNameContaining(keyword, pageable);
        return products.map(ProductResponseDto::new);
    }

    @Transactional(readOnly = true)
    public List<PopularSearchResponseDto> findByPopulation() {

        List<SearchV1> top10 = searchRepository.findTop10ByOrderByCountDesc();

         return IntStream.range(0, top10.size())
                .mapToObj(i -> new PopularSearchResponseDto(i + 1, top10.get(i).getKeyword()))
                .toList();
    }
}
