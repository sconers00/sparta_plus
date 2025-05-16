package com.example.plusteamproject.domain.report.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import com.example.plusteamproject.domain.report.dto.ReportRequestDto;
import com.example.plusteamproject.domain.report.service.ReportService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class ReportController {

	private final ReportService reportService;

	@PostMapping("/products/{productId}/reports")
	public ResponseEntity<?> createReport(
		@PathVariable Long productId,
		@RequestBody ReportRequestDto requestDto,
		@RequestHeader("Authorization") String token) {
		reportService.createReport(productId, requestDto, token);
		return null;
	}

	@GetMapping ("/users/{userId}/reports")
	public ResponseEntity<?> findReportsBySeller(@PathVariable Long userId) {
		reportService.getReportsBySeller(userId);
		return null;
	}
	@GetMapping("/products/{productId}/reports")
	public ResponseEntity<?> findReportsByProduct(@PathVariable Long productId) {
		reportService.getReportsByProduct(productId);
		return null;
	}

	@DeleteMapping("/reports/{id}")
	public ResponseEntity<?> deleteReport(
		@PathVariable Long id,
		@RequestHeader("Authorization") String token
	) {
		reportService.deleteReport(id, token);
		return null;
	}
}
