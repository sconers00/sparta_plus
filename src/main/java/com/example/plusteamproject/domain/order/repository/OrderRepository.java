package com.example.plusteamproject.domain.order.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.plusteamproject.domain.order.entity.Order;

@Repository
public interface OrderRepository extends JpaRepository<Order,Long>{
	@Query("select o from Order o left join fetch o.userId u where o.userId=:id")
	List<Order>findByUserId(@Param("id") Long id);
}
