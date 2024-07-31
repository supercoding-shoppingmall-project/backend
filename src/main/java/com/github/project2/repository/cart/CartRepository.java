package com.github.project2.repository.cart;

import com.github.project2.entity.cart.CartEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CartRepository extends JpaRepository<CartEntity, Integer> {
    Optional<CartEntity> findByUserId(Integer userId);
}
