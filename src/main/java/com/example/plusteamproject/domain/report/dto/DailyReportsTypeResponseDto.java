package com.example.plusteamproject.domain.report.dto;

import java.time.LocalDateTime;

import com.example.plusteamproject.domain.report.entity.ReportType;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class DailyReportsTypeResponseDto {
	private final Long id;
	private final ReportType reportType;
	private final LocalDateTime createdAt;
}
