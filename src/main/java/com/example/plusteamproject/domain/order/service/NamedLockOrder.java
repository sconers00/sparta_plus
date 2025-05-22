package com.example.plusteamproject.domain.order.service;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import com.example.plusteamproject.common.lock.LockRepository;
import com.example.plusteamproject.domain.order.dto.OrderRequestDto;
import com.example.plusteamproject.security.CustomUserDetail;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class NamedLockOrder {
	private final LockRepository lockRepository;
	private final OrderService orderService;

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
}
