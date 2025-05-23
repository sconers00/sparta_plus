package com.example.plusteamproject.domain.report.service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.example.plusteamproject.domain.product.entity.Product;
import com.example.plusteamproject.domain.product.entity.ProductCategory;
import com.example.plusteamproject.domain.product.repository.ProductRepository;
import com.example.plusteamproject.domain.report.entity.Report;
import com.example.plusteamproject.domain.report.entity.ReportType;
import com.example.plusteamproject.domain.report.repository.ReportRepository;
import com.example.plusteamproject.domain.user.dto.request.CreateUserRequestDto;
import com.example.plusteamproject.domain.user.entity.User;
import com.example.plusteamproject.domain.user.repository.UserRepository;

@SpringBootTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class ReportServiceTest {

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private ProductRepository productRepository;

	@Autowired
	private ReportRepository reportRepository;

	// // 더미 만들기
	// @BeforeAll
	// void setupData() {
	// 	users = generateUsers();
	// 	products = generateProducts();
	// 	generateReports();
	// }
	//
	// private List<User> users;
	// private List<Product> products;
	// private Random random = new Random();
	//
	//
	// private List<User> generateUsers() {
	// 	List<User> users = new ArrayList<>();
	//
	// 	for (int i = 0; i < 10000; i++) {
	// 		CreateUserRequestDto dto = new CreateUserRequestDto(
	// 			"x" + i,                // email
	// 			"y" + i,                // name
	// 			"z" + i,                // nickname
	// 			"d",                    // password
	// 			"010-" + i,             // phone
	// 			"USER"                  // enum만 정확히 넣으면 OK
	// 		);
	// 		users.add(new User(dto, "pw" + i));
	// 	}
	// 	userRepository.saveAll(users);
	// 	return users;
	// }
	//
	//
	// private List<Product> generateProducts() {
	// 	List<Product> products = new ArrayList<>();
	// 	for (int i = 0; i < 5000; i++) {
	// 		products.add(Product.of(
	// 			ProductCategory.FOOD,
	// 			"A",
	// 			"B",
	// 			BigDecimal.valueOf(1000),
	// 			100L,
	// 			users.get(random.nextInt(users.size()))));
	// 	}
	// 	productRepository.saveAll(products);
	// 	return products;
	// }
	//
	// private void generateReports() {
	// 	List<Report> reports = new ArrayList<>();
	// 	for (int i = 0; i < 100000; i++) {
	// 		User randomUser = users.get(random.nextInt(users.size()));
	// 		Product randomProduct = products.get(random.nextInt(products.size()));
	// 		ReportType type = ReportType.values()[random.nextInt(ReportType.values().length)];
	//
	// 		Report report = new Report("신고 내용 " + i, type, randomUser, randomProduct);
	// 		report.setCreatedAtManually(LocalDateTime.now().minusDays(random.nextInt(365)));
	// 		reports.add(report);
	// 	}
	// 	reportRepository.saveAll(reports);
	// }

	@Test
	void countDailyReports() {

		LocalDateTime day = LocalDateTime.now().minusDays(1);

		// 시작 시간
		long start = System.currentTimeMillis();

		// 성능 측정 대상 메서드 호출
		reportRepository.countByReportTypeDaily(day);

		// 종료 시간
		long duration = System.currentTimeMillis() - start;

		System.out.println("⏱️ 쿼리 수행 시간: " + duration + "ms");

	}


}