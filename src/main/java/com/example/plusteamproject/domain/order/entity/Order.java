package com.example.plusteamproject.domain.order.entity;

import java.math.BigDecimal;

import com.example.plusteamproject.common.BaseEntity;
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
import lombok.Setter;

@Getter
@NoArgsConstructor(access= AccessLevel.PROTECTED)
@Entity
@Table(name="order")
public class Order extends BaseEntity {
	@Id
	@GeneratedValue(strategy= GenerationType.IDENTITY)
	@Column(name="order_id")
	private Long orderId;
	@Setter
	@Column(name="payment_method")
	private String paymentMethod;
	@Setter
	@Column(name="quantity")
	private Long quantity;
	@Setter
	@Column(name="total_price")
	private BigDecimal totalPrice;
	@Setter
	@Column(name="address")
	private String address;
	@Setter
	@Column(name="status", nullable=false, columnDefinition = "varchar(30) DEFAULT 'PENDING'")
	@Enumerated(EnumType.STRING)
	private OrderStatus orderStatus;
	@ManyToOne
	@Column(name="user_id")
	private User userId;
	@Setter
	@ManyToOne
	@Column(name="product_id")
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

	public void chageStatus(String orderStatus) {
		this.orderStatus = OrderStatus.of(orderStatus);
	}
}
