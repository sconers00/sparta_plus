package com.example.plusteamproject.domain.report.controller;

import java.time.LocalDate;
import java.util.List;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.plusteamproject.common.ApiResponse;
import com.example.plusteamproject.domain.report.dto.DailyReportsTypeResponseDto;
import com.example.plusteamproject.domain.report.dto.ReportRequestDto;
import com.example.plusteamproject.domain.report.dto.ReportTypeCountDto;
import com.example.plusteamproject.domain.report.entity.ReportType;
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

	// 가장 많이 신고받은 제품 TOP 5 조회
	@GetMapping("/products/reports/top5")
	public ResponseEntity<?> GetTop5ReportedProducts() {

		reportService.GetTop5ReportedProducts(5);

		return ResponseEntity
			.status(HttpStatus.OK)
			.body(new ApiResponse<>("가장 많이 신고받은 제품 TOP 5 조회목록입니다.", reportService.GetTop5ReportedProducts(5)));
	}

	// 최근 일주일 간 신고 유형별 집계 - -indexing 미 적용
	@GetMapping("/reports/daily")
	public ResponseEntity<?> getDailyReportType(){

		long start = System.currentTimeMillis();
		List<ReportTypeCountDto> result = reportService.countDailyReports();
		long end = System.currentTimeMillis();
		long runTime = end - start;

		return ResponseEntity
			.status(HttpStatus.OK)
			.body(new ApiResponse<>("최근 일주일 간 신고 유형별 집계 내역입니다. Indexing 미 적용 응답 시간 : " + runTime +"ms", result));
	}

	// 최근 일주일 간 신고 유형별 집계 -indexing 적용
	@GetMapping("/reports/daily-indexing")
	public ResponseEntity<?> getDailyReportTypeByIndexing(){

		long start = System.currentTimeMillis();
		List<ReportTypeCountDto> result = reportService.countDailyReports();
		long end = System.currentTimeMillis();
		long runTime = end - start;

		return ResponseEntity
			.status(HttpStatus.OK)
			.body(new ApiResponse<>("최근 일주일 간 신고 유형별 집계 내역입니다. Indexing 적용 응답 시간 : " + runTime +"ms", result));
	}

	/** Indexing DDL 쿼리
	 성능 실험 (탐색 필드 수 및 인덱싱 적용 여부 확인 가능)
	 EXPLAIN
	 SELECT report_type, COUNT(*)
	 FROM reports
	 WHERE created_at >= NOW() - INTERVAL 1DAY
	 GROUP BY report_type;

	 index 생성 / 삭제
	 CREATE INDEX idx_report_created_type ON plusteamproject.reports(created_at, report_type);
	 DROP INDEX idx_report_created_type ON plusteamproject.reports;
	 */


	// 하루동안 접수된 신고 조회(신고유형만) - Indexing 성능 확인용 - RequesParam ver
	@GetMapping("/reports/daily/search")
	public ResponseEntity<?> getDailyReportByIndexing(
		@RequestParam("day") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate day,
		@RequestParam("type") ReportType reportType) {

		long start = System.currentTimeMillis();
		List<DailyReportsTypeResponseDto> result = reportService.GetDailyReportsType(day, reportType);
		long end = System.currentTimeMillis();
		long runTime = end - start;

		return ResponseEntity
			.status(HttpStatus.OK)
			.body(new ApiResponse<>("해당 날짜에 접수된 선택하신 신고 유형 목록입니다. Indexing 적용 응답 시간 : " + runTime +"ms", result));
	}

	// 하루동안 접수된 신고 조회(신고유형만) - Indexing 성능 확인용 - nO RequesParam ver
	@GetMapping("/reports/daily/search-noparam")
	public ResponseEntity<?> getDailyReportByIndexing() {

		long start = System.currentTimeMillis();
		List<DailyReportsTypeResponseDto> result = reportService.GetDailyReportsTypeNoParam();
		long end = System.currentTimeMillis();
		long runTime = end - start;

		return ResponseEntity
			.status(HttpStatus.OK)
			.body(new ApiResponse<>("해당 날짜에 접수된 선택하신 신고 유형 목록입니다. Indexing 적용 응답 시간 : " + runTime +"ms", result));
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

	// 신고 더미 데이터 생성
	@PostMapping("/reports/dummies")
	public ResponseEntity<?> generateReportDummies() {

		reportService.dummyReports();

		return ResponseEntity
			.status(HttpStatus.OK)
			.body(new ApiResponse<>("신고 더미 데이터 생성 완료"));
	}

}
