package com.example.plusteamproject.domain.report.entity;

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
@Table(name = "report")
@NoArgsConstructor
public class Report extends BaseEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column
	private String content;

	@Enumerated(EnumType.STRING)
	@Column
	private ReportType reportType;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(referencedColumnName = "user_id", name = "reportId")
	private User reporter;

	// @ManyToOne(fetch = FetchType.LAZY)
	// @JoinColumn(referencedColumnName = "product_id", name = "productId")
	// private Product product;




}
