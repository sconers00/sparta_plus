package com.example.plusteamproject.domain.report.entity;

import java.time.LocalDateTime;

import com.example.plusteamproject.common.BaseEntity;
import com.example.plusteamproject.domain.product.entity.Product;
import com.example.plusteamproject.domain.user.entity.User;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@Table(name = "reports")
@NoArgsConstructor
public class Report extends BaseEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(nullable = false)
	private String content;

	@Enumerated(EnumType.STRING)
	@Column(nullable = false)
	private ReportType reportType;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "reporter_Id", nullable = false)
	private User reporter;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "product_Id", nullable = false)
	private Product product;

	public Report(String content, ReportType reportType, User reporter, Product product) {
		this.content = content;
		this.reportType = reportType;
		this.reporter = reporter;
		this.product = product;
	}

	public void setCreatedAtManually(LocalDateTime createdAt) {
		this.setCreatedAt(createdAt);
	}
}