package com.example.plusteamproject.domain.report.dto;

import com.example.plusteamproject.domain.report.entity.ReportType;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ReportTypeCountDto {
	private final ReportType reportType;
	private final Long count;

}
