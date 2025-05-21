package com.example.plusteamproject.domain.search.service;

import com.example.plusteamproject.domain.product.dto.ProductResponseDto;
import com.example.plusteamproject.domain.product.entity.Product;
import com.example.plusteamproject.domain.product.repository.ProductRepository;
import com.example.plusteamproject.domain.search.dto.PopularSearchResponseDto;
import com.example.plusteamproject.domain.search.entity.Search;
import com.example.plusteamproject.domain.search.repository.SearchRepository;
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
public class SearchService {

    private final SearchRepository searchRepository;
    private final ProductRepository productRepository;

    @Transactional
    public Slice<ProductResponseDto> findByProductName(String keyword) {

        Search search = searchRepository.findByKeyword(keyword).orElseGet(() -> searchRepository.save(new Search(keyword)));
        search.increaseCount();

        Pageable pageable = PageRequest.of(0,10);

        Slice<Product> products = productRepository.findByProductNameContaining(keyword, pageable);
        return products.map(ProductResponseDto::new);
    }

    @Transactional(readOnly = true)
    public List<PopularSearchResponseDto> findByPopulation() {

        List<Search> top10 = searchRepository.findTop10ByOrderByCountDesc();

         return IntStream.range(0, top10.size())
                .mapToObj(i -> new PopularSearchResponseDto(i + 1, top10.get(i).getKeyword()))
                .toList();
    }
}
