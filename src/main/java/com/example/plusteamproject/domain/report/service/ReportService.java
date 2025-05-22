package com.example.plusteamproject.domain.report.service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Random;
import java.util.stream.Collectors;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import com.example.plusteamproject.domain.product.entity.Product;
import com.example.plusteamproject.domain.product.repository.ProductRepository;
import com.example.plusteamproject.domain.report.dto.ProductReportCountDto;
import com.example.plusteamproject.domain.report.dto.ReportRequestDto;
import com.example.plusteamproject.domain.report.dto.ReportResponseDto;
import com.example.plusteamproject.domain.report.dto.ReportTypeCountDto;
import com.example.plusteamproject.domain.report.entity.Report;
import com.example.plusteamproject.domain.report.entity.ReportType;
import com.example.plusteamproject.domain.report.repository.ReportRepository;
import com.example.plusteamproject.domain.user.entity.User;
import com.example.plusteamproject.domain.user.repository.UserRepository;
import com.example.plusteamproject.security.CustomUserDetail;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ReportService {

	private final ReportRepository reportRepository;
	private final ProductRepository productRepository;
	private final UserRepository userRepository;

	// 신고하기
	@Transactional
	public void createReport(Long productId, ReportRequestDto requestDto, CustomUserDetail userDetail) {


	Product product = productRepository.findById(productId).orElseThrow(
		() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "해당 제품이 존재하지 않습니다."));

	Long userId = userDetail.getUser().getId();

	boolean exists = reportRepository.existsByReporter_IdAndProduct_Id(userId, productId);
	if (exists) {
		throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "사용자님께서는 이미 해당 제품에 대해 신고하셨습니다.");
	}

	Report report = new Report(requestDto.getContent(), requestDto.getReportType(), userDetail.getUser(), product);
	reportRepository.save(report);

	}

	// 판매자가 받은 신고 내역 조회
	@Transactional(readOnly = true)
	public List<ReportResponseDto> getReportsBySeller(Long userId) {
	List<Report> reports = reportRepository.findByProduct_User_Id(userId);

		return reports.stream()
			.map(ReportResponseDto::new)
			.collect(Collectors.toList());
	}

	// 제품이 받은 신고 내역 조회
	@Transactional(readOnly = true)
	public List<ReportResponseDto> getReportsByProduct(Long productId) {
	List<Report> reports = reportRepository.findByProduct_Id(productId);

		return reports.stream()
			.map(ReportResponseDto::new)
			.collect(Collectors.toList());
	}


	// 가장 많이 신고받은 제품 TOP 5 조회 <- 실제로 가장 많이 조회될 것 같은 기능이므로 인덱싱 선정!
	public List<ProductReportCountDto> GetTop5ReportedProducts(int limit) {
		Pageable pageable = PageRequest.of(0, limit);
		return reportRepository.findTop5ProductReportCounts(pageable);
	}

	// 최근 일주일 간 신고 유형별 집계
	public List<ReportTypeCountDto> countWeeklyReports() {
		LocalDateTime weekly = LocalDateTime.now().minusDays(7);
		return reportRepository.countByReportTypeWeekly(weekly);
	}

	// 신고 삭제(철회)
	@Transactional
	public void deleteReport(Long id, CustomUserDetail userDetail) {

		Report report = reportRepository.findByIdOrElseThrow(id);

		String userNameByToken = userDetail.getUsername();
		String userNameByReportId = report.getReporter().getEmail();


		if(!userNameByToken.equals(userNameByReportId)) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "잘못된 접근입니다.");
		}

		reportRepository.delete(report);
	}

	@Transactional
	public void dummyReports() {
		List<User> users = userRepository.findAll();
		List<Product> products = productRepository.findAll();
		ReportType[] types = ReportType.values();

		if (users.isEmpty() || products.isEmpty()) {
			throw new IllegalStateException("User 또는 Product가 없습니다.");
		}

		Random random = new Random();
		List<Report> reports = new ArrayList<>();

		for (int i = 0; i < 100000; i++) {
			User reporter = users.get(random.nextInt(users.size()));
			Product product = products.get(random.nextInt(products.size()));
			ReportType randomType = types[random.nextInt(types.length)];
			LocalDateTime randomCreatedAt = getRandomCreatedAt();

			Report report = new Report(
				"더미 신고 내용 " + i,
				randomType,
				reporter,
				product
			);
			report.setCreatedAtManually(randomCreatedAt);

			reports.add(report);
		}

		reportRepository.saveAll(reports);
	}

	private LocalDateTime getRandomCreatedAt() {
		Random random = new Random();
		int daysAgo = random.nextInt(30); // 최근 30일
		int seconds = random.nextInt(24 * 60 * 60); // 하루 중 랜덤 시간
		return LocalDateTime.now().minusDays(daysAgo).minusSeconds(seconds);
	}
}
