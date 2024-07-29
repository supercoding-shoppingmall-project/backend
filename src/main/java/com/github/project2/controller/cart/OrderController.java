//package com.github.project2.controller.cart;
//
//import com.github.project2.dto.cart.OrderRequestDto;
//import com.github.project2.service.cart.OrderService;
//import lombok.RequiredArgsConstructor;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.*;
//
//@RestController
//@RequestMapping("/api/checkout")
//@RequiredArgsConstructor
//public class OrderController {
//
//    private final OrderService orderService;
//
//    // 주문 처리 요청
//    @PostMapping
//    public ResponseEntity<Void> checkout(@RequestParam String userEmail,
//                                         @RequestBody OrderRequestDto orderRequest) {
//        orderService.checkout(userEmail, orderRequest);
//        return ResponseEntity.ok().build();
//    }
//}
