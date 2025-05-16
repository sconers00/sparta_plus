package com.example.plusteamproject.domain.report.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.plusteamproject.domain.report.service.ReportService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class ReportController {

	private final ReportService reportService;

	@PostMapping("/products/{productId}/reports")
	public ResponseEntity<?> createReport() {
		reportService.createReport();
		return null;
	}

	@GetMapping ("/users/{userId}/reports")
	public ResponseEntity<?> findReportsBySeller() {
		reportService.getReportsBySeller();
		return null;
	}
	@GetMapping("/products/{productId}/reports")
	public ResponseEntity<?> findReportsByProduct() {
		reportService.getReportsByProduct();
		return null;
	}

	@DeleteMapping("/reports/{id}")
	public ResponseEntity<?> deleteReport() {
		reportService.deleteReport();
		return null;
	}
}
