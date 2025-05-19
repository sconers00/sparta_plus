package com.example.plusteamproject.domain.order.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.plusteamproject.common.ApiResponse;
import com.example.plusteamproject.domain.order.dto.OrderRequestDto;
import com.example.plusteamproject.domain.order.dto.OrderResponseDto;
import com.example.plusteamproject.domain.order.dto.OrderStatusDto;
import com.example.plusteamproject.domain.order.service.OrderService;
import com.example.plusteamproject.security.CustomUserDetail;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
@RequestMapping("/orders")
@Validated
public class OrderController {
	private final OrderService orderService;
	@PostMapping
	public ResponseEntity<ApiResponse<OrderResponseDto>> createOrder(@RequestBody OrderRequestDto dto, @AuthenticationPrincipal CustomUserDetail userDetail){
		OrderResponseDto orderResponseDto = orderService.saveOrder(dto, userDetail);
		return ResponseEntity
			.status(HttpStatus.CREATED)
			.body(new ApiResponse<>("주문이 완료되었습니다.", orderResponseDto));
	}

	@GetMapping//전체조회 추가
	public ResponseEntity<List<OrderResponseDto>> findByUserId(@AuthenticationPrincipal CustomUserDetail userDetail){
		List<OrderResponseDto> dto = orderService.findByUserId(userDetail);
		return new ResponseEntity<>(dto,HttpStatus.OK);
	}

	@GetMapping("/{order_id}")//단일조회 추가
	public ResponseEntity<OrderResponseDto> findById(@PathVariable Long order_id,@AuthenticationPrincipal CustomUserDetail userDetail){
		OrderResponseDto dto = orderService.findById(order_id,userDetail);
		return new ResponseEntity<>(dto,HttpStatus.OK);
	}

	@PatchMapping("/{order_id}")
	public ResponseEntity<ApiResponse<Void>> updateOrder(@PathVariable Long order_id, @RequestBody OrderRequestDto dto, @AuthenticationPrincipal CustomUserDetail userDetail){
		orderService.updateOrder(order_id, dto,userDetail);
		return ResponseEntity
			.status(HttpStatus.OK)
			.body(new ApiResponse<>("주문이 수정되었습니다."));
	}

	@DeleteMapping("/{order_id}")
	public ResponseEntity<ApiResponse<Void>> deleteOrder(@PathVariable Long order_id, @AuthenticationPrincipal CustomUserDetail userDetail){
		orderService.deleteOrder(order_id, userDetail);
		return ResponseEntity
			.status(HttpStatus.OK)
			.body(new ApiResponse<>("주문이 취소되었습니다."));
	}

	@GetMapping("/{order_id}/status")
	public ResponseEntity<ApiResponse<OrderStatusDto>> findOrderStatus(@PathVariable Long order_id, @AuthenticationPrincipal CustomUserDetail userDetail){
		OrderStatusDto orderStatusDto = orderService.findOrderStatus(order_id, userDetail);
		return ResponseEntity
			.status(HttpStatus.OK)
			.body(new ApiResponse<>("",orderStatusDto));
	}

	@PatchMapping("/status")
	public ResponseEntity<ApiResponse<OrderStatusDto>> updateOrderStatus(@RequestBody OrderStatusDto dto, @AuthenticationPrincipal CustomUserDetail userDetail){
		OrderStatusDto orderStatusDto = orderService.updateOrderStatus(dto, userDetail);
		return ResponseEntity
			.status(HttpStatus.OK)
			.body(new ApiResponse<>("주문 상태가 수정되었습니다.",orderStatusDto));
	}
}
