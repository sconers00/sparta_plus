package com.example.plusteamproject.domain.searchV1.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class PopularSearchResponseDto {

    private final int rank;

    private final String keyword;
}
