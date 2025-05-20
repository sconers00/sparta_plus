package com.example.plusteamproject.domain.order.dto;

import java.math.BigDecimal;

import com.example.plusteamproject.domain.order.entity.Order;
import com.example.plusteamproject.domain.order.entity.OrderStatus;

import lombok.Getter;

@Getter
public class OrderResponseDto {
	private final Long orderId;
	private final String paymentMethod;
	private final Long quantity;
	private final BigDecimal totalPrice;
	private final String address;
	private final OrderStatus orderStatus;
	private final Long userId;
	private final Long productId;
	public OrderResponseDto(Long orderId, String paymentMethod, Long quantity, BigDecimal totalPrice, String address, OrderStatus orderStatus, Long userId, Long productId){
		this.orderId=orderId;
		this.paymentMethod=paymentMethod;
		this.quantity=quantity;
		this.totalPrice=totalPrice;
		this.address=address;
		this.orderStatus=orderStatus;
		this.userId=userId;
		this.productId=productId;
	}

	public static OrderResponseDto toDto(Order order){
		return new OrderResponseDto(
			order.getOrderId(),
			order.getPaymentMethod(),
			order.getQuantity(),
			order.getTotalPrice(),
			order.getAddress(),
			order.getOrderStatus(),
			order.getUserId().getId(),
			order.getProductId().getId()
		);
	}
}
