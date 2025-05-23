package com.example.plusteamproject.domain.report.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ProductReportCountDto {
	private final Long productId;
	private final String productName;
	private final String sellerName;
	private final Long reportCount;
}
