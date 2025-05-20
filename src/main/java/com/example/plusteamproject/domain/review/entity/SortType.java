package com.example.plusteamproject.domain.review.entity;

public enum SortType {

    LATEST, // 최신순
    SCORE_DESC, // 별점 높은 순
    SCORE_ASC; // 별점 낮은 순

    public static SortType from(String value) {
        if (value == null) {
            return LATEST;
        }

        try {
            return SortType.valueOf(value.toUpperCase());
        } catch (IllegalArgumentException e) {
            return LATEST;
        }
    }
}

