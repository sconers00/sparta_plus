package com.example.plusteamproject.domain.report.repository;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import com.example.plusteamproject.domain.report.dto.ProductReportCountDto;
import com.example.plusteamproject.domain.report.dto.ReportTypeCountDto;
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

	@Query("""
        SELECT new com.example.plusteamproject.domain.report.dto.ProductReportCountDto(
            r.product.id, r.product.name, r.product.user.name, COUNT(r.id))
        FROM Report r
        GROUP BY r.product.id, r.product.name, r.product.user.name
        ORDER BY COUNT(r.id) DESC
    """)
	List<ProductReportCountDto> findTop5ProductReportCounts(Pageable pageable);

	@Query("""
	SELECT new com.example.plusteamproject.domain.report.dto.ReportTypeCountDto(
		r.reportType, COUNT(r))
	FROM Report r
	WHERE r.createdAt >= :weekly
	GROUP BY r.reportType
	""")
	List<ReportTypeCountDto> countByReportTypeWeekly(LocalDateTime weekly);
}
