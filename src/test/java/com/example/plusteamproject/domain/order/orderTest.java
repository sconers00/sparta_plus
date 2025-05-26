package com.example.plusteamproject.domain.order;


import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;


import java.math.BigDecimal;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.function.Consumer;

import static org.junit.jupiter.api.Assertions.*;

import com.example.plusteamproject.domain.order.controller.OrderController;
import com.example.plusteamproject.domain.order.dto.OrderRequestDto;
import com.example.plusteamproject.domain.order.repository.OrderRepository;
import com.example.plusteamproject.domain.order.service.OrderFacade;
import com.example.plusteamproject.domain.order.service.OrderService;
import com.example.plusteamproject.domain.product.entity.Product;
import com.example.plusteamproject.domain.product.entity.ProductCategory;
import com.example.plusteamproject.domain.product.repository.ProductRepository;
import com.example.plusteamproject.domain.user.dto.request.CreateUserRequestDto;
import com.example.plusteamproject.domain.user.entity.User;
import com.example.plusteamproject.domain.user.repository.UserRepository;
import com.example.plusteamproject.security.CustomUserDetail;

@SpringBootTest
class IntegrationOrderTest {

	@Autowired
	OrderController orderController;

	@Autowired
	OrderService OrderService;

	@Autowired
	OrderRepository orderRepository;

	@Autowired
	OrderFacade orderFacade;

	@Autowired
	ProductRepository productRepository;

	@Autowired
	UserRepository userRepository;

	private final Integer CONCURRENT_COUNT = 100;

	@BeforeEach
	public void before() {
		CreateUserRequestDto UDto1= new CreateUserRequestDto("email@test","name","nick","password1","010-1234-5678","ADMIN");
		User user1 = new User(UDto1, "password1");
		userRepository.saveAndFlush(user1);
		CreateUserRequestDto UDto2= new CreateUserRequestDto("email2@test","name2","nick2","password2","010-1234-5679","USER");
		User user2 = new User(UDto2,"password2");
		userRepository.saveAndFlush(user2);
		Product product = Product.of(ProductCategory.valueOf("TOYS"),"test","testdesc",BigDecimal.valueOf(3000),5000L,user1);
		productRepository.saveAndFlush(product);
	}

	@AfterEach
	public void after() {
		orderRepository.deleteAll();
		productRepository.deleteAll();
		userRepository.deleteAll();
	}

	private void DoOrderTest(Consumer<Void> action) throws InterruptedException {
		Long originQuantity = productRepository.findById(1L).orElseThrow().getQuantity();

		ExecutorService executorService = Executors.newFixedThreadPool(32);
		CountDownLatch latch = new CountDownLatch(CONCURRENT_COUNT);

		for (int i = 0; i < CONCURRENT_COUNT; i++) {
			executorService.submit(() -> {
				try {
					action.accept(null);
				} finally {
					latch.countDown();
				}
			});
		}

		latch.await();

		Product product = productRepository.findById(1L).orElseThrow();
		assertEquals(originQuantity - CONCURRENT_COUNT, product.getQuantity());
	}

	@Test
	@DisplayName("동시에 100명의 주문 : 동시성 이슈")
	public void badOrderTest() throws Exception {
		User user2 = userRepository.findById(2L).orElseThrow(Exception::new);
		CustomUserDetail userDetail = new CustomUserDetail(user2);
		Product product = productRepository.findById(1L).orElseThrow(Exception::new);
		OrderRequestDto dto=new OrderRequestDto("Method",1L,"address",product);
		DoOrderTest((_no) -> orderController.createOrder(dto, userDetail));
	}

	@Test
	@DisplayName("동시에 100명의 주문 : 분산락")
	public void redissonOrderTest() throws Exception {
		User user2 = userRepository.findById(2L).orElseThrow(Exception::new);
		CustomUserDetail userDetail = new CustomUserDetail(user2);
		Product product = productRepository.findById(1L).orElseThrow(Exception::new);
		OrderRequestDto dto=new OrderRequestDto("Method",1L,"address",product);
		DoOrderTest((_no) -> orderController.createOrder(dto, userDetail));
	}

}
