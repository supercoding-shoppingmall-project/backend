package com.github.project2.repository.cart;

import com.github.project2.entity.cart.CartEntity;
import com.github.project2.entity.cart.CartItemEntity;
import com.github.project2.entity.user.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CartItemRepository extends JpaRepository<CartItemEntity, Integer> {
	void deleteAllByCart(CartEntity cart);
}
