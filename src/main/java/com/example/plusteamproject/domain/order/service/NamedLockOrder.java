package com.example.plusteamproject.domain.order.service;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import com.example.plusteamproject.common.lock.LockRepository;
import com.example.plusteamproject.domain.order.dto.OrderRequestDto;
import com.example.plusteamproject.domain.order.entity.Order;
import com.example.plusteamproject.domain.order.repository.OrderRepository;
import com.example.plusteamproject.security.CustomUserDetail;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class NamedLockOrder {//네임드락. redisson을 사용시 주석처리해주세요
	private final LockRepository lockRepository;
	private final OrderService orderService;
	private final OrderRepository orderRepository;

	@Transactional
	public void getOrderLock(OrderRequestDto dto, CustomUserDetail userDetail) {
		StringBuilder name = new StringBuilder();
		name.append(dto.getProductId().getName());
		name.append(dto.getProductId().getId());
		try {
			lockRepository.getLock(name.toString());
			orderService.saveOrderV2(dto, userDetail);
		} catch(Exception e){
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"오류발생");}
		finally{lockRepository.releaseLock(name.toString());
		}
	}
	@Transactional
	public void getOrderLocks(Long id, OrderRequestDto dto, CustomUserDetail userDetail) {
		StringBuilder name1 = new StringBuilder();
		StringBuilder name2 = new StringBuilder();
		name1.append(dto.getProductId().getName());
		name1.append(dto.getProductId().getId());
		Order order = orderRepository.findById(id).orElseThrow(
			()-> new ResponseStatusException(HttpStatus.NOT_FOUND));
		name2.append(order.getProductId().getName());
		name2.append(order.getProductId().getId());
		try {
			lockRepository.getLock(name1.toString());
			lockRepository.getLock(name2.toString());
			orderService.updateOrderV2(order, dto, userDetail);
		} catch(Exception e){
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"오류발생");}
		finally{lockRepository.releaseLock(name1.toString());
			lockRepository.releaseLock(name2.toString());
		}
	}
}
