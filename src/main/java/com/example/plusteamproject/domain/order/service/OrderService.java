package com.example.plusteamproject.domain.order.service;

import org.springframework.stereotype.Service;

import com.example.plusteamproject.domain.order.dto.OrderModifyDto;
import com.example.plusteamproject.domain.order.dto.OrderRequestDto;
import com.example.plusteamproject.domain.order.dto.OrderResponseDto;
import com.example.plusteamproject.domain.order.dto.OrderStatusDto;
import com.example.plusteamproject.domain.order.repository.OrderRepository;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class OrderService {

	private final OrderRepository orderRepository;

	public static OrderResponseDto save(OrderRequestDto dto, HttpServletRequest request) {
	}

	public static OrderModifyDto updateOrder(Long orderId, OrderModifyDto dto, HttpServletRequest request) {
	}

	public static void deleteOrder(Long orderId, HttpServletRequest request) {
	}

	public static OrderStatusDto findOrderStatus(Long orderId, HttpServletRequest request) {
	}

	public static OrderStatusDto updateOrderStatus(OrderStatusDto dto, HttpServletRequest request) {
	}
}
