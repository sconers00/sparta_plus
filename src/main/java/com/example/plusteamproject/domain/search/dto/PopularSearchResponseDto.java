package com.example.plusteamproject.domain.search.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class PopularSearchResponseDto {

    private final int rank;

    private final String keyword;
}
