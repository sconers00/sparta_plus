package com.example.plusteamproject.domain.report.repository;

import java.util.List;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import com.example.plusteamproject.domain.report.entity.Report;

public interface ReportRepository extends JpaRepository <Report, Long> {


	default Report findByIdOrElseThrow(Long id) {
		return findById(id).orElseThrow(
			() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "해당 신고 건이 존재하지 않습니다."));
	}

	@EntityGraph(attributePaths = {"reporter", "product"})
	List<Report> findByProduct_User_Id(Long userId);

	@EntityGraph(attributePaths = {"reporter", "product"})
	List<Report> findByProduct_Id(Long productId);

	boolean existsByReporter_IdAndProduct_Id(Long userId, Long productId);
}
