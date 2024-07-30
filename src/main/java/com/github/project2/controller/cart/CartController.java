//package com.github.project2.controller.cart;
//
//import com.github.project2.dto.cart.CartItemDto;
//import com.github.project2.service.cart.CartService;
//import lombok.RequiredArgsConstructor;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.*;
//
//import java.util.List;
//
//@RestController
//@RequestMapping("/api/cart")
//@RequiredArgsConstructor
//public class CartController {
//
//    private final CartService cartService;
//
//    // 사용자의 장바구니 항목 조회
//    @GetMapping
//    public ResponseEntity<List<CartItemDto>> getCartItems(@RequestParam String userEmail) {
//        List<CartItemDto> cartItems = cartService.getCartItems(userEmail);
//        return ResponseEntity.ok(cartItems);
//    }
//
//    // 장바구니에 항목 추가
//    @PostMapping
//    public ResponseEntity<Void> addCartItem(@RequestParam String userEmail,
//                                            @RequestParam Integer productId,
//                                            @RequestParam String size,
//                                            @RequestParam Integer quantity) {
//        cartService.addCartItem(userEmail, productId, size, quantity);
//        return ResponseEntity.ok().build();
//    }
//
//    // 장바구니 항목 수량 업데이트
//    @PutMapping("/{cartItemId}")
//    public ResponseEntity<Void> updateCartItemQuantity(@PathVariable Integer cartItemId,
//                                                       @RequestParam Integer quantity) {
//        cartService.updateCartItemQuantity(cartItemId, quantity);
//        return ResponseEntity.ok().build();
//    }
//
//    // 장바구니 항목 삭제
//    @DeleteMapping("/{cartItemId}")
//    public ResponseEntity<Void> removeCartItem(@PathVariable Integer cartItemId) {
//        cartService.removeCartItem(cartItemId);
//        return ResponseEntity.ok().build();
//    }
//}
