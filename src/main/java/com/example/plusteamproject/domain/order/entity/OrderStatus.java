package com.example.plusteamproject.domain.order.entity;

import java.util.Arrays;

public enum OrderStatus {
	PENDING, PREPARED, SHIPPING, ARRIVED;

	public static OrderStatus of(String orderStatus) {
		return Arrays.stream(OrderStatus.values())
			.filter(r -> r.name().equalsIgnoreCase(orderStatus))
			.findFirst()
			.orElseThrow();
	}
}
