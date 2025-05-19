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
import com.example.plusteamproject.domain.user.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ReportService {

	private final ReportRepository reportRepository;
	private final UserRepository userRepository;
	// private final ProductRepository productRepository;

	// 신고하기
	public void createReport(Long productId, ReportRequestDto requestDto, String token) {

	// boolean exists = reportRepository.existsByProductId(productId);
	// if (exists) {
	// 	throw new RuntimeException(HttpStatus.BAD_REQUEST, "이미 해당 제품에 대해 신고하셨습니다.");
	// }
	// 	Product product = productRepository.findById(productId);
	// 	String productName = product.getProductName();
	//  Long sellerId = product.getSellerId();
	// Report report = new Report(requestDto.getContent(), requestDto.getReportType(), productName, sellerId);
	// reportRepository.save(report);



	}

	// 판매자가 받은 신고 내역 조회
	public List<ReportResponseDto> getReportsBySeller(Long userId) {
	List<Report> reports = reportRepository.findByProduct_User_Id(userId);

		return reports.stream()
			.map(ReportResponseDto::new)
			.collect(Collectors.toList());
	}

	// 제품이 받은 신고 내역 조회
	public List<ReportResponseDto> getReportsByProduct(Long productId) {
	List<Report> reports = reportRepository.findByProduct_Id(productId);

		return reports.stream()
			.map(ReportResponseDto::new)
			.collect(Collectors.toList());
	}


	// 신고 삭제(철회)
	@Transactional
	public void deleteReport(Long id, String token) {
		Report report = reportRepository.findByIdOrElseThrow(id);
		reportRepository.delete(report);
	}

}
