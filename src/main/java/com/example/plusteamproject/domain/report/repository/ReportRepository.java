package com.example.plusteamproject.domain.report.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.plusteamproject.domain.report.entity.Report;

public interface ReportRepository extends JpaRepository <Report, Long> {
}
