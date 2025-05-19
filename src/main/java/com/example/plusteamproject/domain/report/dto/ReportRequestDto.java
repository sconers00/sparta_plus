package com.example.plusteamproject.domain.report.dto;

import com.example.plusteamproject.domain.report.entity.ReportType;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class ReportRequestDto {

	private final String content;

	private final ReportType reportType;


}
