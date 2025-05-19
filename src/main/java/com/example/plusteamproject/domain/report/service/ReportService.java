package com.example.plusteamproject.domain.report.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import com.example.plusteamproject.domain.product.entity.Product;
import com.example.plusteamproject.domain.product.repository.ProductRepository;
import com.example.plusteamproject.domain.report.dto.ReportRequestDto;
import com.example.plusteamproject.domain.report.dto.ReportResponseDto;
import com.example.plusteamproject.domain.report.entity.Report;
import com.example.plusteamproject.domain.report.repository.ReportRepository;
import com.example.plusteamproject.domain.user.entity.User;
import com.example.plusteamproject.security.CustomUserDetail;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ReportService {

	private final ReportRepository reportRepository;
	private final ProductRepository productRepository;

	// 신고하기
	@Transactional
	public void createReport(Long productId, ReportRequestDto requestDto, CustomUserDetail userDetail) {


	Product product = productRepository.findById(productId).orElseThrow(
		() -> new RuntimeException("해당 제품이 존재하지 않습니다."));

	Long userId = userDetail.getUser().getId();

	boolean exists = reportRepository.existsByUser_Id(userId);
	if (exists) {
		throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "사용자님께서는 이미 해당 제품에 대해 신고하셨습니다.");
	}

	/* 제품 신고 자격 검증 로직 ( 고려사항 )
	if (!userDetail.getUser().equals(product.getUser())) {
		throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "사용자님께서는 해당 제품을 구매하신 이력이 없습니다.");
	}
	 */

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


	// 신고 삭제(철회)
	@Transactional
	public void deleteReport(Long id, CustomUserDetail userDetail) {
		User tokenByUser = userDetail.getUser();

		Report report = reportRepository.findByIdOrElseThrow(id);
		User idByUser = report.getReporter();

		if(!tokenByUser.equals(idByUser)) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "잘못된 접근입니다.");
		}

		reportRepository.delete(report);
	}
}
