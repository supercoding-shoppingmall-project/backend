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

    /**
     * 장바구니에 물품을 추가하는 엔드포인트.
     *
     * @param userId           장바구니에 물품을 추가할 사용자 ID
     * @param cartItemRequest  추가할 물품 정보가 담긴 요청 본문
     * @return                 추가된 물품의 정보를 담은 응답 본문
     */
    @PostMapping("/{userId}")
    public ResponseEntity<CartItemResponse> addToCart(@PathVariable Integer userId, @RequestBody CartItemRequest cartItemRequest) {
        // 장바구니에 물품을 추가하고 응답 객체 생성
        CartItemResponse cartItemResponse = cartService.addToCart(userId, cartItemRequest);
        // 응답 본문과 상태 코드를 반환
        return new ResponseEntity<>(cartItemResponse, HttpStatus.CREATED);
    }

    /**
     * 특정 사용자의 장바구니를 조회하는 엔드포인트.
     *
     * @param userId  장바구니를 조회할 사용자 ID
     * @return        사용자의 장바구니 정보를 담은 응답 본문
     */
    @GetMapping("/{userId}")
    public ResponseEntity<CartResponse> getCart(@PathVariable Integer userId) {
        // 사용자의 장바구니를 조회하고 응답 객체 생성
        CartResponse cartResponse = cartService.getCartByUserId(userId);
        // 응답 본문과 상태 코드를 반환
        return new ResponseEntity<>(cartResponse, HttpStatus.OK);
    }

    /**
     * 장바구니에서 특정 물품을 삭제하는 엔드포인트.
     *
     * @param userId      장바구니에서 물품을 삭제할 사용자 ID
     * @param cartItemId  삭제할 물품의 ID
     * @return            삭제된 물품의 정보를 담은 응답 본문
     */
    @DeleteMapping("/{userId}/items/{cartItemId}")
    public ResponseEntity<CartItemResponse> removeCartItem(@PathVariable Integer userId, @PathVariable Integer cartItemId) {
        // 장바구니에서 물품을 삭제하고 응답 객체 생성
        CartItemResponse cartItemResponse = cartService.removeCartItem(userId, cartItemId);
        // 응답 본문과 상태 코드를 반환
        return new ResponseEntity<>(cartItemResponse, HttpStatus.OK);
    }
}
