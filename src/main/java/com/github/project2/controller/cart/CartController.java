package com.github.project2.controller.cart;

import com.github.project2.dto.cart.CartItemRequest;
import com.github.project2.dto.cart.CartItemResponse;
import com.github.project2.dto.cart.CartResponse;
import com.github.project2.service.cart.CartService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/cart")
@RequiredArgsConstructor
public class CartController {
    private final CartService cartService;

    @PostMapping("/{userId}")
    public ResponseEntity<CartItemResponse> addToCart(@PathVariable Integer userId, @RequestBody CartItemRequest cartItemRequest) {
        CartItemResponse cartItemResponse = cartService.addToCart(userId, cartItemRequest);
        return new ResponseEntity<>(cartItemResponse, HttpStatus.CREATED);
    }

    @GetMapping("/{userId}")
    public ResponseEntity<CartResponse> getCart(@PathVariable Integer userId) {
        CartResponse cartResponse = cartService.getCartByUserId(userId);
        return new ResponseEntity<>(cartResponse, HttpStatus.OK);
    }

    @DeleteMapping("/{userId}/items/{cartItemId}")
    public ResponseEntity<Void> removeCartItem(@PathVariable Integer userId, @PathVariable Integer cartItemId) {
        cartService.removeCartItem(userId, cartItemId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
