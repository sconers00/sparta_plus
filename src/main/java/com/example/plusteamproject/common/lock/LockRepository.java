package com.example.plusteamproject.common.lock;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.example.plusteamproject.domain.order.entity.Order;

public interface LockRepository extends JpaRepository<Order, Long> {

	@Query(value = "select get_lock(:key, 10)", nativeQuery = true)//네임드락. redisson을 사용시 주석처리해주세요
	void getLock(String key);

	@Query(value = "select release_lock(:key)", nativeQuery = true)
	void releaseLock(String key);
}
