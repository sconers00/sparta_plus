package com.example.plusteamproject.domain.report.service;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.example.plusteamproject.domain.product.entity.Product;
import com.example.plusteamproject.domain.product.repository.ProductRepository;
import com.example.plusteamproject.domain.report.dto.ReportRequestDto;
import com.example.plusteamproject.domain.report.entity.Report;
import com.example.plusteamproject.domain.report.repository.ReportRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ReportService {

	private final ReportRepository reportRepository;

	// private final ProductRepository productRepository;

	// 신고하기
	public void createReport(Long productId, ReportRequestDto requestDto, String token) {

	// boolean exists = reportRepository.existsByProductId(productId);
	// if (exists) {
	// 	throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "이미 해당 제품에 대해 신고하셨습니다.");
	// }
	// 	Product product = productRepository.findById(productId);
	// 	String productName = product.getProductName();


	}

	// 판매자 신고 내역 조회
	public void getReportsBySeller(Long userId) {

	}
	// 제품 신고 내역 조회
	public void getReportsByProduct(Long productId) {

	}

	// 신고 삭제(철회)
	public void deleteReport(Long id, String token) {

	}

}
