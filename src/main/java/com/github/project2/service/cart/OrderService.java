//package com.github.project2.service.cart;
//
//import com.github.project2.dto.cart.OrderRequestDto;
//import com.github.project2.entity.cart.OrderEntity;
//import com.github.project2.entity.cart.OrderItemEntity;
//import com.github.project2.entity.post.ProductEntity;
//import com.github.project2.entity.user.UserEntity;
//import com.github.project2.repository.cart.OrderRepository;
//import com.github.project2.repository.post.ProductRepository;
//import com.github.project2.repository.user.UserRepository;
//import com.github.project2.service.exceptions.NotFoundException;
//import lombok.RequiredArgsConstructor;
//import org.springframework.stereotype.Service;
//import org.springframework.transaction.annotation.Transactional;
//
//import java.math.BigDecimal;
//import java.time.LocalDateTime;
//import java.util.List;
//import java.util.stream.Collectors;
//
//@Service
//@RequiredArgsConstructor
//public class OrderService {
//    private final OrderRepository orderRepository;
//    private final ProductRepository productRepository;
//    private final UserRepository userRepository;
//
//    // 주문 처리
//    @Transactional
//    public void checkout(String userEmail, OrderRequestDto orderRequest) {
//        UserEntity user = userRepository.findUserByEmail(userEmail);
//
//        // 총 주문 금액 계산
//        BigDecimal totalPrice = orderRequest.getItems().stream()
//                .map(item -> {
//                    ProductEntity product = productRepository.findById(item.getProductId())
//                            .orElseThrow(() -> new NotFoundException("상품을 찾을 수 없습니다."));
//                    return product.getPrice().multiply(new BigDecimal(item.getQuantity()));
//                })
//                .reduce(BigDecimal.ZERO, BigDecimal::add);
//
////        // 사용자 잔액 확인
////        if (user.getBalance().compareTo(totalPrice) < 0) {
////            throw new InvalidValueException("잔액이 부족합니다.");
////        }
//
//        // 주문 엔티티 생성 및 설정
//        OrderEntity order = new OrderEntity();
//        order.setUser(user);
//        order.setTotalPrice(totalPrice);
//        order.setCreatedAt(LocalDateTime.now());
//        order.setShippingAddress(orderRequest.getShippingAddress());
//
//        // 주문 항목 엔티티 리스트 생성 및 설정
//        List<OrderItemEntity> orderItems = orderRequest.getItems().stream().map(itemDto -> {
//            ProductEntity product = productRepository.findById(itemDto.getProductId())
//                    .orElseThrow(() -> new NotFoundException("상품을 찾을 수 없습니다."));
//            OrderItemEntity orderItem = new OrderItemEntity();
//            orderItem.setOrder(order);
//            orderItem.setProduct(product);
//            orderItem.setQuantity(itemDto.getQuantity());
//            orderItem.setSize(itemDto.getSize());
//            return orderItem;
//        }).collect(Collectors.toList());
//
//        // 주문에 주문 항목 설정
//        order.setOrderItems(orderItems);
//        orderRepository.save(order); // 주문 저장
//
////        // 사용자 잔액 업데이트
////        user.setBalance(user.getBalance().subtract(totalPrice));
////        userRepository.save(user);
//    }
//}
