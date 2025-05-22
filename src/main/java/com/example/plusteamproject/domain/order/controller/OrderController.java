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
import org.springframework.web.server.ResponseStatusException;

import com.example.plusteamproject.common.ApiResponse;
import com.example.plusteamproject.domain.order.dto.OrderRequestDto;
import com.example.plusteamproject.domain.order.dto.OrderResponseDto;
import com.example.plusteamproject.domain.order.dto.OrderStatusDto;
import com.example.plusteamproject.domain.order.entity.OrderStatus;
import com.example.plusteamproject.domain.order.service.NamedLockOrder;
import com.example.plusteamproject.domain.order.service.OrderService;
import com.example.plusteamproject.security.CustomUserDetail;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RequiredArgsConstructor
@RestController
@RequestMapping("/orders")
@Validated
@Slf4j
public class OrderController {
	private final OrderService orderService;
	private final NamedLockOrder namedLockOrder;
	@PostMapping
	public ResponseEntity<ApiResponse<OrderResponseDto>> createOrder(@Valid @RequestBody OrderRequestDto dto, @AuthenticationPrincipal CustomUserDetail userDetail){
		OrderResponseDto orderResponseDto = orderService.saveOrder(dto, userDetail);
		return ResponseEntity
			.status(HttpStatus.CREATED)
			.body(new ApiResponse<>("주문이 완료되었습니다.", orderResponseDto));
	}
	@PostMapping("/v2")
	public ResponseEntity<ApiResponse> createOrderV2(@Valid @RequestBody OrderRequestDto dto, @AuthenticationPrincipal CustomUserDetail userDetail){
		namedLockOrder.getOrderLock(dto, userDetail);
		return ResponseEntity
			.status(HttpStatus.CREATED)
			.body(new ApiResponse<>("주문이 완료되었습니다."));
	}

	@GetMapping//사용자의 모든 주문 조회 추가
	public ResponseEntity<List<OrderResponseDto>> findByUserId(@AuthenticationPrincipal CustomUserDetail userDetail){
		List<OrderResponseDto> dto = orderService.findByUserId(userDetail);
		return new ResponseEntity<>(dto,HttpStatus.OK);
	}

	@GetMapping("/{order_id}")//사용자의 단일 주문 조회 추가
	public ResponseEntity<OrderResponseDto> findById(@PathVariable Long order_id,@AuthenticationPrincipal CustomUserDetail userDetail){
		OrderResponseDto dto = orderService.findById(order_id,userDetail);
		return new ResponseEntity<>(dto,HttpStatus.OK);
	}

	@PatchMapping("/{order_id}")
	public ResponseEntity<ApiResponse<Void>> updateOrder(@PathVariable Long order_id, @Valid @RequestBody OrderRequestDto dto, @AuthenticationPrincipal CustomUserDetail userDetail){
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
	public ResponseEntity<ApiResponse<OrderStatusDto>> updateOrderStatus(@Valid @RequestBody OrderStatusDto dto, @AuthenticationPrincipal CustomUserDetail userDetail){
		enumChecker(dto);
		OrderStatusDto orderStatusDto = orderService.updateOrderStatus(dto, userDetail);
		return ResponseEntity
			.status(HttpStatus.OK)
			.body(new ApiResponse<>("주문 상태가 수정되었습니다.",orderStatusDto));
	}

	public void enumChecker(OrderStatusDto dto){
		try{OrderStatus orderStatus =OrderStatus.valueOf(dto.getOrderStatus());
		}catch(IllegalArgumentException e){
			log.warn("주문상태 dto 내용 식별 실패. dto가 잘못 작성되었거나 검증로직 오류.");
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST);}
	}
}
