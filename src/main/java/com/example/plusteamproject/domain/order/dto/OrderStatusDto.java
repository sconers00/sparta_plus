package com.example.plusteamproject.domain.order.dto;

import com.example.plusteamproject.domain.order.entity.OrderStatus;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
public class OrderStatusDto {
	@NotNull
	private final Long orderId;
	@NotNull
	private final Long productId;
	@Setter
	@NotBlank
	private final OrderStatus orderStatus;

	public OrderStatusDto(Long orderId, Long productId, OrderStatus orderStatus){
		this.orderId=orderId;
		this.productId=productId;
		this.orderStatus=orderStatus;
	}
}
