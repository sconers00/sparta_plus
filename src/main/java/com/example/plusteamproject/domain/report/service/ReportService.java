package com.example.plusteamproject.domain.report.service;

import org.springframework.stereotype.Service;

import com.example.plusteamproject.domain.report.repository.ReportRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ReportService {

	private final ReportRepository reportRepository;


	// 신고하기
	public void createReport() {

	}

	// 판매자 신고 내역 조회
	public void getReportsBySeller() {

	}
	// 제품 신고 내역 조회
	public void getReportsByProduct() {

	}

	// 신고 삭제(철회)
	public void deleteReport() {

	}
}
