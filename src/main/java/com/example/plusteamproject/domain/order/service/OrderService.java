package com.example.plusteamproject.domain.order.service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import com.example.plusteamproject.domain.order.dto.OrderRequestDto;
import com.example.plusteamproject.domain.order.dto.OrderResponseDto;
import com.example.plusteamproject.domain.order.dto.OrderStatusDto;
import com.example.plusteamproject.domain.order.entity.Order;
import com.example.plusteamproject.domain.order.entity.OrderStatus;
import com.example.plusteamproject.domain.order.repository.OrderRepository;
import com.example.plusteamproject.domain.product.dto.ProductUpdateRequestDto;
import com.example.plusteamproject.domain.product.entity.Product;
import com.example.plusteamproject.domain.product.repository.ProductRepository;
import com.example.plusteamproject.domain.user.entity.User;
import com.example.plusteamproject.security.CustomUserDetail;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class OrderService {

	private final OrderRepository orderRepository;
	private final ProductRepository productRepository;

	public OrderResponseDto saveOrder(OrderRequestDto dto, CustomUserDetail userDetail) {
		User user = userDetail.getUser();
		Product product = productRepository.findById(dto.getProductId().getId())
			.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

		Order order = new Order(dto.getPaymentMethod(), dto.getQuantity(), product.getPrice().multiply(
			BigDecimal.valueOf(dto.getQuantity())), dto.getAddress(), OrderStatus.valueOf("PENDING"), user,product);
		orderRepository.save(order);
		return orderReturn(order);
	}
	@Transactional(propagation = Propagation.REQUIRES_NEW)//
	public void saveOrderV2(OrderRequestDto dto, CustomUserDetail userDetail) {
		User user = userDetail.getUser();
		Product product = productRepository.findById(dto.getProductId().getId())
			.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
		Order order = new Order(dto.getPaymentMethod(), dto.getQuantity(), product.getPrice().multiply(
			BigDecimal.valueOf(dto.getQuantity())), dto.getAddress(), OrderStatus.valueOf("PENDING"), user,product);
		Long remain = product.getQuantity()-dto.getQuantity();
		if(remain<0)
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
		ProductUpdateRequestDto productUpdateRequestDto = new ProductUpdateRequestDto(product.getCategory(),product.getName(),product.getContent(),product.getPrice(),remain);
		product.update(productUpdateRequestDto);
		orderRepository.saveAndFlush(order);
	}

	public List<OrderResponseDto> findByUserId(CustomUserDetail userDetail) {//사용자의 모든 주문 조회(로그인정보기반)
		User user = userDetail.getUser();
		List<Order> orderList= orderRepository.findByUserId_Id(user.getId());
		return orderList.stream().map(OrderResponseDto::toDto).collect(Collectors.toList());
	}

	public OrderResponseDto findById(Long orderId, CustomUserDetail userDetail) {//사용자의 특정 주문 조회(로그인정보+url)
		User user = userDetail.getUser();
		Order order = orderRepository.findById(orderId).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
		if(!Objects.equals(user.getId(), order.getUserId().getId()))
			throw new ResponseStatusException(HttpStatus.FORBIDDEN);//주문자 본인만 조회 가능
		return orderReturn(order);
	}

	@Transactional
	public void updateOrder(Long orderId, OrderRequestDto dto, CustomUserDetail userDetail) {//수정기
		User user = userDetail.getUser();
		Order order = orderRepository.findById(orderId).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
		if(!Objects.equals(user.getId(),order.getUserId().getId()))
			throw new ResponseStatusException(HttpStatus.FORBIDDEN);
		if(!order.getOrderStatus().equals(OrderStatus.valueOf("PENDING")))
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST);//주문이 대기중이 아니라면 수정 불가.
		Product product = productRepository.findById(dto.getProductId().getId())
			.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
		order.update(dto, product.getPrice());
	}
	@Transactional
	public void updateOrderV2(Order order, OrderRequestDto dto, CustomUserDetail userDetail) {//수정기
		User user = userDetail.getUser();
		if(!Objects.equals(user.getId(),order.getUserId().getId()))
			throw new ResponseStatusException(HttpStatus.FORBIDDEN);
		if(!order.getOrderStatus().equals(OrderStatus.valueOf("PENDING")))
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST);//주문이 대기중이 아니라면 수정 불가.
		Product past = productRepository.findById(order.getProductId().getId())//주문 수정 대상 product
			.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
		Long quantity = past.getQuantity()+order.getQuantity();
		ProductUpdateRequestDto cancelDto = new ProductUpdateRequestDto(past.getCategory(), past.getName(), past.getContent(), past.getPrice(),quantity);
		past.update(cancelDto);//주문 수정 대상 product의 quantity를 복구
		Product product = productRepository.findById(dto.getProductId().getId())//주문 수정으로 주문될 product
			.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
		Long remain = product.getQuantity()-dto.getQuantity();
		ProductUpdateRequestDto productUpdateRequestDto = new ProductUpdateRequestDto(product.getCategory(),product.getName(),product.getContent(),product.getPrice(),remain);
		product.update(productUpdateRequestDto);//주문수정 결과 주문될 product의 quantity 감소 업데이트
		order.update(dto,product.getPrice());//주문 업데이트
	}

	@Transactional
	public void deleteOrder(Long orderId, CustomUserDetail userDetail) {//하드딜리트, 주문자 본인만 주문 취소 가능.
		User user = userDetail.getUser();
		Order order = orderRepository.findById(orderId).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
		if(!Objects.equals(user.getId(),order.getUserId().getId()))
			throw new ResponseStatusException(HttpStatus.FORBIDDEN);
		if(!order.getOrderStatus().equals(OrderStatus.valueOf("PENDING")))
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST);//주문이 대기중이 아니라면 취소 불가.
		orderRepository.deleteById(orderId);
	}

	public OrderStatusDto findOrderStatus(Long orderId, CustomUserDetail userDetail) {
		User user = userDetail.getUser();
		Order order = orderRepository.findById(orderId).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
		if(!Objects.equals(user.getId(),order.getProductId().getUser().getId())
			&&!Objects.equals(user.getId(),order.getUserId().getId()))
			throw new ResponseStatusException(HttpStatus.FORBIDDEN);
		return orderStatusReturn(order);
	}

	@Transactional
	public OrderStatusDto updateOrderStatus(OrderStatusDto dto, CustomUserDetail userDetail) {
		User user = userDetail.getUser();
		Order order = orderRepository.findById(dto.getOrderId()).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
		if(!Objects.equals(user.getId(),order.getProductId().getUser().getId())
			&&!Objects.equals(user.getId(),order.getUserId().getId()))
			throw new ResponseStatusException(HttpStatus.FORBIDDEN);
		if((order.getOrderStatus()).equals(OrderStatus.valueOf("ARRIVED")))
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
		order.updateStatus(dto);
		return orderStatusReturn(order);
	}
	public OrderResponseDto orderReturn(Order order){//주문반환기
		return new OrderResponseDto(
			order.getOrderId(),
			order.getPaymentMethod(),
			order.getQuantity(),
			new BigDecimal(String.valueOf(order.getTotalPrice())),
			order.getAddress(),
			order.getOrderStatus(),
			order.getUserId().getId(),
			order.getProductId().getId()
		);
	}
	public OrderStatusDto orderStatusReturn(Order order){//주문상태 반환기
		return new OrderStatusDto(
			order.getOrderId(),
			order.getProductId().getId(),
			order.getOrderStatus().toString()
		);
	}
}
