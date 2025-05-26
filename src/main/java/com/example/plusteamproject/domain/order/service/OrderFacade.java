package com.example.plusteamproject.domain.order.service;

import java.util.concurrent.TimeUnit;

import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.example.plusteamproject.domain.order.dto.OrderRequestDto;
import com.example.plusteamproject.domain.order.entity.Order;
import com.example.plusteamproject.domain.order.repository.OrderRepository;
import com.example.plusteamproject.security.CustomUserDetail;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class OrderFacade {//redisson. 네임드락을 사용시 주석처리해주세요

	private final RedissonClient redissonClient;
	private final OrderService orderService;
	private final OrderRepository orderRepository;

	public void createOrderRedis(OrderRequestDto dto, CustomUserDetail userDetail) {
		RLock lock = redissonClient.getLock(String.format("orderInfo : %s %s", dto.getProductId().getId(),dto.getProductId().getName()));
		try {
			if (!lock.tryLock(20, 1, TimeUnit.SECONDS)) {
				System.out.println("락 획득 대기 시간이 만료되었습니다.");
			}
			orderService.saveOrder(dto,userDetail);
		} catch (InterruptedException e) {
			throw new RuntimeException(e);
		} finally {
			lock.unlock();
		}
	}

	public void updateOrderRedis(Long id,OrderRequestDto dto, CustomUserDetail userDetail) {
		RLock lock = redissonClient.getLock(String.format("orderInfo : %s %s", dto.getProductId().getId(),dto.getProductId().getName()));
		try {
			if (!lock.tryLock(20, 1, TimeUnit.SECONDS)) {
				System.out.println("락 획득 대기 시간이 만료되었습니다.");
			}
			Order order = orderRepository.findById(id).orElseThrow(
				()-> new ResponseStatusException(HttpStatus.NOT_FOUND));
			orderService.updateOrder(order, dto, userDetail);
		} catch (InterruptedException e) {
			throw new RuntimeException("인터럽트 발생",e);
		} catch (ResponseStatusException e){
			throw new ResponseStatusException(HttpStatus.NOT_FOUND);
		} catch (Exception e){
			throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR);
		}finally {
			lock.unlock();
		}
	}
}
