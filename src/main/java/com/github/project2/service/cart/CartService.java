//package com.github.project2.service.cart;
//
//import com.github.project2.dto.cart.CartItemDto;
//import com.github.project2.entity.cart.CartItemEntity;
//import com.github.project2.entity.post.ProductEntity;
//import com.github.project2.entity.user.UserEntity;
//import com.github.project2.repository.cart.CartItemRepository;
//import com.github.project2.repository.post.ProductRepository;
//import com.github.project2.repository.user.UserRepository;
//import com.github.project2.service.exceptions.NotFoundException;
//import lombok.RequiredArgsConstructor;
//import org.springframework.stereotype.Service;
//
//import java.util.List;
//import java.util.stream.Collectors;
//
//@Service
//@RequiredArgsConstructor
//public class CartService {
//    private final CartItemRepository cartItemRepository;
//    private final ProductRepository productRepository;
//    private final UserRepository userRepository;
//
//    // 사용자의 장바구니 항목을 조회하여 DTO로 변환
//    public List<CartItemDto> getCartItems(String userEmail) {
//        UserEntity user = userRepository.findUserByEmail(userEmail);
//        List<CartItemEntity> cartItems = cartItemRepository.findByUser(user);
//
//        return cartItems.stream().map(this::convertToDto).collect(Collectors.toList());
//    }
//
//    // 장바구니에 항목 추가
//    public void addCartItem(String userEmail, Integer productId, String size, Integer quantity) {
//        UserEntity user = userRepository.findUserByEmail(userEmail);
//        ProductEntity product = productRepository.findById(productId)
//                .orElseThrow(() -> new NotFoundException("상품을 찾을 수 없습니다."));
//
//        CartItemEntity cartItem = new CartItemEntity();
//        cartItem.setUser(user);
//        cartItem.setProduct(product);
//        cartItem.setSize(size);
//        cartItem.setQuantity(quantity);
//
//        cartItemRepository.save(cartItem);
//    }
//
//    // 장바구니 항목 수량 업데이트
//    public void updateCartItemQuantity(Integer cartItemId, Integer quantity) {
//        CartItemEntity cartItem = cartItemRepository.findById(cartItemId)
//                .orElseThrow(() -> new NotFoundException("장바구니 항목을 찾을 수 없습니다."));
//        cartItem.setQuantity(quantity);
//        cartItemRepository.save(cartItem);
//    }
//
//    // 장바구니 항목 삭제
//    public void removeCartItem(Integer cartItemId) {
//        CartItemEntity cartItem = cartItemRepository.findById(cartItemId)
//                .orElseThrow(() -> new NotFoundException("장바구니 항목을 찾을 수 없습니다."));
//        cartItemRepository.delete(cartItem);
//    }
//
//    // 엔티티를 DTO로 변환
//    private CartItemDto convertToDto(CartItemEntity cartItem) {
//        return new CartItemDto(
//                cartItem.getId(),
//                cartItem.getProduct().getName(),
//                cartItem.getProductCategory().getName(),
//                cartItem.getSize(),
//                cartItem.getQuantity(),
//                cartItem.getProduct().getPrice(),
//                cartItem.getUser().getAddress()
//        );
//    }
//}
