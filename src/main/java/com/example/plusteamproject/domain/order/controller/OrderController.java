package com.example.plusteamproject.domain.order.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.plusteamproject.domain.order.dto.OrderModifyDto;
import com.example.plusteamproject.domain.order.dto.OrderRequestDto;
import com.example.plusteamproject.domain.order.dto.OrderResponseDto;
import com.example.plusteamproject.domain.order.dto.OrderStatusDto;
import com.example.plusteamproject.domain.order.service.OrderService;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
@RequestMapping("/orders")
public class OrderController {
	private final OrderService orderService;
	@PostMapping
	public ResponseEntity<OrderResponseDto> createOrder(@RequestBody OrderRequestDto dto, HttpServletRequest request){
		OrderResponseDto orderResponseDto = OrderService.save(dto, request);
		return new ResponseEntity<>(orderResponseDto, HttpStatus.CREATED);
	}

	@PatchMapping("/{order_id}")
	public ResponseEntity<OrderModifyDto> updateOrder(@PathVariable Long orderId, @RequestBody OrderModifyDto dto, HttpServletRequest request){
		OrderModifyDto orderModifyDto = OrderService.updateOrder(orderId, dto,request);
		return new ResponseEntity<>(orderModifyDto,HttpStatus.OK);
	}

	@DeleteMapping("/{order_id}")
	public void deleteOrder(@PathVariable Long orderId, HttpServletRequest request){
		OrderService.deleteOrder(orderId, request);
	}

	@GetMapping("/{order_id}/status")
	public ResponseEntity<OrderStatusDto> findOrderStatus(@PathVariable Long orderId, HttpServletRequest request){
		OrderStatusDto orderStatusDto = OrderService.findOrderStatus(orderId, request);
		return new ResponseEntity<>(orderStatusDto, HttpStatus.OK);
	}

	@PatchMapping("/status")
	public ResponseEntity<OrderStatusDto> updateOrderStatus(@RequestBody OrderStatusDto dto, HttpServletRequest request){
		OrderStatusDto orderStatusDto = OrderService.updateOrderStatus(dto, request);
		return new ResponseEntity<>(orderStatusDto, HttpStatus.OK);
	}
}
