package com.example.plusteamproject.domain.order.entity;

import com.example.plusteamproject.common.commonEntity.BaseEntity;
import com.example.plusteamproject.domain.product.entity.Product;
import com.example.plusteamproject.domain.user.entity.User;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access= AccessLevel.PROTECTED)
@Entity
@Table(name="order")
public class Order extends BaseEntity {
	@Id
	@GeneratedValue(strategy= GenerationType.IDENTITY)
	@Column(name="order_id")
	private Long orderId;
	@Column(name="payment_method")
	private String paymentMethod;
	@Column(name="quantity")
	private Long quantity;
	@Column(name="total_price")
	private Long totalPrice;
	@Column(name="address")
	private String address;
	@Enumerated(EnumType.STRING)
	@Column(name="status")
	private OrderStatus orderStatus;
	@ManyToOne
	@Column(name="user_id")
	private User userId;
	@ManyToOne
	@Column(name="product_id")
	private Product product_id;

	public Order(Long orderId, String paymentMethod, Long quantity, Long totalPrice, String address, OrderStatus orderStatus, User userId, Product productId){
		this.orderId=orderId;
		this.paymentMethod=paymentMethod;
		this.quantity=quantity;
		this.totalPrice=totalPrice;
		this.address=address;
		this.orderStatus=orderStatus;
		this.userId=userId;
		this.productId=productId;
	}
}
