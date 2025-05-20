package com.example.plusteamproject.domain.order.dto;

import com.example.plusteamproject.domain.product.entity.Product;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;

@Getter
public class OrderRequestDto {
	@NotBlank
	private final String paymentMethod;
	@NotNull
	private final Long quantity;
	@NotBlank
	private final String address;
	@NotNull
	private final Product productId;

	public OrderRequestDto(String paymentMethod, Long quantity, String address, Product productId){
		this.paymentMethod=paymentMethod;
		this.quantity=quantity;
		this.address=address;
		this.productId=productId;
	}
}
