package com.example.plusteamproject.domain.order.repository;

import java.util.List;

import com.example.plusteamproject.domain.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.plusteamproject.domain.order.entity.Order;

@Repository
public interface OrderRepository extends JpaRepository<Order,Long>{

	List<Order> findByUserId_Id(Long userIdId);
}
