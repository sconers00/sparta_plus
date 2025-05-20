package com.example.plusteamproject.domain.order.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;

@Getter
public class OrderStatusDto {
	@NotNull
	private final Long orderId;
	@NotNull
	private final Long productId;
	@NotBlank
	private final String orderStatus;

	public OrderStatusDto(Long orderId, Long productId, String orderStatus){
		this.orderId=orderId;
		this.productId=productId;
		this.orderStatus=orderStatus;
	}
}
