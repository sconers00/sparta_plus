package com.example.plusteamproject.common.lock;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.example.plusteamproject.domain.order.entity.Order;

public interface LockRepository extends JpaRepository<Order, Long> {

	@Query(value = "select get_lock(:key, 10)", nativeQuery = true)//테스트용, 최종버전에서는 300으로 설정됩니다.
	void getLock(String key);

	@Query(value = "select release_lock(:key)", nativeQuery = true)
	void releaseLock(String key);
}
