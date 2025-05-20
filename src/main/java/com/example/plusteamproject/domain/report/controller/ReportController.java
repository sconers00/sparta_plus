package com.example.plusteamproject.domain.report.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.plusteamproject.common.ApiResponse;
import com.example.plusteamproject.domain.report.dto.ReportRequestDto;
import com.example.plusteamproject.domain.report.service.ReportService;
import com.example.plusteamproject.security.CustomUserDetail;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class ReportController {

	private final ReportService reportService;

	@PostMapping("/products/{productId}/reports")
	public ResponseEntity<?> createReport(
		@PathVariable Long productId,
		@RequestBody @Valid ReportRequestDto requestDto,
		@AuthenticationPrincipal CustomUserDetail userDetail) {

		reportService.createReport(productId, requestDto, userDetail);

		return ResponseEntity
			.status(HttpStatus.CREATED)
			.body(new ApiResponse<>("제품 신고를 완료했습니다"));
	}

	@GetMapping ("/users/{userId}/reports")
	public ResponseEntity<?> findReportsBySeller(@PathVariable Long userId) {

		reportService.getReportsBySeller(userId);

		return ResponseEntity
			.status(HttpStatus.OK)
			.body(new ApiResponse<>("해당 판매자에게 접수된 신고 목록을 조회했습니다", reportService.getReportsBySeller(userId)));
	}
	@GetMapping("/products/{productId}/reports")
	public ResponseEntity<?> findReportsByProduct(@PathVariable Long productId) {

		reportService.getReportsByProduct(productId);

		return ResponseEntity
			.status(HttpStatus.OK)
			.body(new ApiResponse<>("해당 제품에 접수된 신고 목록을 조회했습니다", reportService.getReportsByProduct(productId)));
	}

	@DeleteMapping("/reports/{id}")
	public ResponseEntity<?> deleteReport(
		@PathVariable Long id,
		@AuthenticationPrincipal CustomUserDetail userDetail) {

		reportService.deleteReport(id, userDetail);

		return ResponseEntity
			.status(HttpStatus.OK)
			.body(new ApiResponse<>("해당 제품에 대한 신고를 취소했습니다"));
	}
}
