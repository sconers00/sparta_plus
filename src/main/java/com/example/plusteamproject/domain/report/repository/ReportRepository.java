package com.example.plusteamproject.domain.report.repository;

import java.util.List;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import com.example.plusteamproject.domain.report.entity.Report;

public interface ReportRepository extends JpaRepository <Report, Long> {


	default Report findByIdOrElseThrow(Long id) {
		return findById(id).orElseThrow(
			() -> new RuntimeException(""));
	}

	@EntityGraph(attributePaths = {"reporter", "product"})
	List<Report> findByProduct_User_Id(Long userId);

	@EntityGraph(attributePaths = {"reporter", "product"})
	List<Report> findByProduct_Id(Long productId);

	boolean existsByUser_IdAndProduct_Id(Long userId, Long productId);
}
