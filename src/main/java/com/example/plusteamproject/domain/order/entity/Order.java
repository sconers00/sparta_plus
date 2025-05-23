package com.example.plusteamproject.domain.order.entity;

import java.math.BigDecimal;

import com.example.plusteamproject.common.BaseEntity;
import com.example.plusteamproject.domain.order.dto.OrderRequestDto;
import com.example.plusteamproject.domain.order.dto.OrderStatusDto;
import com.example.plusteamproject.domain.product.entity.Product;
import com.example.plusteamproject.domain.user.entity.User;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;


@Getter
@NoArgsConstructor
@Entity
@Table(name="orders")
public class Order extends BaseEntity {
	@Id
	@GeneratedValue(strategy= GenerationType.IDENTITY)
	private Long orderId;
	@Column(nullable=false)
	private String paymentMethod;
	@Column(nullable=false)
	private Long quantity;
	@Column(nullable=false)
	private BigDecimal totalPrice;
	@Column(nullable=false)
	private String address;
	@Column(name="status", nullable=false, columnDefinition = "varchar(30) DEFAULT 'PENDING'")
	@Enumerated(EnumType.STRING)
	private OrderStatus orderStatus;
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="users_id")
	private User userId;
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="product_id")
	private Product productId;

	public Order(String paymentMethod, Long quantity, BigDecimal totalPrice, String address, OrderStatus orderStatus, User userId, Product productId){
		this.paymentMethod=paymentMethod;
		this.quantity=quantity;
		this.totalPrice=totalPrice;
		this.address=address;
		this.orderStatus=orderStatus;
		this.userId=userId;
		this.productId=productId;
	}
	public void update(OrderRequestDto dto,BigDecimal price) {
		if (dto.getPaymentMethod() != null)
			this.paymentMethod = dto.getPaymentMethod();
		if (dto.getQuantity() != null)
			this.quantity = dto.getQuantity();
		if (dto.getAddress() != null)
			this.address = dto.getAddress();
		if (dto.getProductId() != null)
			this.productId = dto.getProductId();
		totalPrice=price.multiply(
			BigDecimal.valueOf(quantity));
	}
	public void updateStatus(OrderStatusDto dto){
		if(dto.getOrderStatus()!=null)
			orderStatus= OrderStatus.valueOf(dto.getOrderStatus());
	}
}
