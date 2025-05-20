package com.example.plusteamproject.domain.report.dto;

import com.example.plusteamproject.domain.report.entity.Report;
import com.example.plusteamproject.domain.report.entity.ReportType;

import lombok.Getter;

@Getter
public class ReportResponseDto {
	private final Long id;

	private final String sellerName;

	private final String productName;

	private final String reporterName;

	private final ReportType reportType;

	private final String content;

	public ReportResponseDto(Report report) {
		this.id = report.getId();
		this.sellerName = report.getProduct().getUser().getName();
		this.productName = report.getProduct().getName();
		this.reporterName = report.getReporter().getName();
		this.reportType = report.getReportType();
		this.content = report.getContent();
	}
}
