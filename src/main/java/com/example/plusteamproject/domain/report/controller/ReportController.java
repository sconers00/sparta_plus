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

	// 신고 생성
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

	// 판매자에게 접수된 신고 이력 조회
	@GetMapping ("/users/{userId}/reports")
	public ResponseEntity<?> findReportsBySeller(@PathVariable Long userId) {

		reportService.getReportsBySeller(userId);

		return ResponseEntity
			.status(HttpStatus.OK)
			.body(new ApiResponse<>("해당 판매자에게 접수된 신고 목록을 조회했습니다", reportService.getReportsBySeller(userId)));
	}

	// 제품에 접수된 신고 이력 조회
	@GetMapping("/products/{productId}/reports")
	public ResponseEntity<?> findReportsByProduct(@PathVariable Long productId) {

		reportService.getReportsByProduct(productId);

		return ResponseEntity
			.status(HttpStatus.OK)
			.body(new ApiResponse<>("해당 제품에 접수된 신고 목록을 조회했습니다", reportService.getReportsByProduct(productId)));
	}

	// 가장 많이 신고받은 제품 TOP 5 조회 <- 실제로 가장 많이 조회될 것 같은 기능이므로 인덱싱 선정!
	// 외래키 기반 인덱싱 <- 기존에 외래키 인덱싱은 되어 있는 상태기 때문에 의미가 없음 <- 효과 X
	@GetMapping("/products/reports/top5")
	public ResponseEntity<?> GetTop5ReportedProducts() {

		reportService.GetTop5ReportedProducts(5);

		return ResponseEntity
			.status(HttpStatus.OK)
			.body(new ApiResponse<>("가장 많이 신고받은 제품 TOP 5 조회목록입니다.", reportService.GetTop5ReportedProducts(5)));
	}

	// 최근 일주일 간 신고 유형별 집계
	@GetMapping("/reports/weekly-type")
	public ResponseEntity<?> getWeeklyReportType(){
		reportService.countWeeklyReports();

		return ResponseEntity
			.status(HttpStatus.OK)
			.body(new ApiResponse<>("최근 일주일 간 신고 유형별 집계 내역입니다.", reportService.countWeeklyReports()));
	}

	// 신고 취소
	@DeleteMapping("/reports/{id}")
	public ResponseEntity<?> deleteReport(
		@PathVariable Long id,
		@AuthenticationPrincipal CustomUserDetail userDetail) {

		reportService.deleteReport(id, userDetail);

		return ResponseEntity
			.status(HttpStatus.OK)
			.body(new ApiResponse<>("해당 제품에 대한 신고를 취소했습니다"));
	}

	@PostMapping("/reports/dummies")
	public ResponseEntity<?> generateReportDummies() {

		reportService.dummyReports();

		return ResponseEntity
			.status(HttpStatus.OK)
			.body(new ApiResponse<>("신고 더미 데이터 생성 완료"));
	}

}
