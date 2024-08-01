package com.github.project2.repository.order;

import com.github.project2.entity.order.OrderEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<OrderEntity, Integer> {
	List<OrderEntity> findByUserId(Integer userId);
}