package com.example.plusteamproject.domain.report.dto;

import com.example.plusteamproject.domain.report.entity.ReportType;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class ReportRequestDto {

	@NotBlank(message = "내용을 입력해주세요.")
	private final String content;

	@NotNull(message = "신고유형을 입력해주세요.")
	private final ReportType reportType;
}
