package com.github.project2.service.cart;

import com.github.project2.dto.cart.CartItemRequest;
import com.github.project2.dto.cart.CartItemResponse;
import com.github.project2.dto.cart.CartResponse;
import com.github.project2.entity.cart.CartEntity;
import com.github.project2.entity.cart.CartItemEntity;
import com.github.project2.entity.post.ProductEntity;
import com.github.project2.entity.post.ProductImageEntity;
import com.github.project2.entity.post.ProductSizeEntity;
import com.github.project2.entity.user.UserEntity;
import com.github.project2.repository.cart.CartItemRepository;
import com.github.project2.repository.cart.CartRepository;
import com.github.project2.repository.post.ProductRepository;
import com.github.project2.repository.post.ProductSizeRepository;
import com.github.project2.repository.user.UserRepository;
import com.github.project2.service.exceptions.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CartService {
    private final CartRepository cartRepository;
    private final ProductRepository productRepository;
    private final CartItemRepository cartItemRepository;
    private final UserRepository userRepository;
    private final ProductSizeRepository productSizeRepository;

    /**
     * 장바구니에 아이템을 추가하는 메서드
     * @param userId 사용자 ID
     * @param cartItemRequest 장바구니 아이템 요청 객체
     * @return 추가된 장바구니 아이템 응답 객체
     */
    @Transactional
    public CartItemResponse addToCart(Integer userId, CartItemRequest cartItemRequest) {
        // 사용자 정보를 조회
        UserEntity user = userRepository.findById(userId)
            .orElseThrow(() -> new NotFoundException("User not found"));

        // 사용자 ID로 장바구니를 조회하거나 새 장바구니 생성
        CartEntity cart = cartRepository.findByUserId(userId)
            .orElseGet(() -> {
                CartEntity newCart = CartEntity.create(user);
                return cartRepository.save(newCart); // 새로운 장바구니를 저장
            });

        // 상품 ID와 사이즈로 상품 사이즈 정보를 조회
        ProductSizeEntity productSize = productSizeRepository.findByProductIdAndSize(cartItemRequest.getProductId(), cartItemRequest.getSize())
            .orElseThrow(() -> new NotFoundException("Product size not found"));

        // 재고가 충분하지 않으면 예외 발생
        if (productSize.getSizeStock() < cartItemRequest.getQuantity()) {
            throw new NotFoundException("Product size out of stock");
        }

        // 장바구니 아이템을 생성하고 저장
        CartItemEntity cartItem = CartItemEntity.create(cart, productSize.getProduct(), cartItemRequest.getQuantity(), productSize.getProduct().getPrice(), userId, cartItemRequest.getSize());
        cartItemRepository.save(cartItem);

        // 장바구니에 새로운 아이템을 추가
        cart.getCartItems().add(cartItem);

        // 장바구니의 총 가격을 업데이트
        updateCartTotalPrice(cart);

        // 상품 이미지 URL을 조회
        String productImageUrl = cartItem.getProduct().getImages().stream()
            .findFirst()
            .map(ProductImageEntity::getImageUrl)
            .orElse(null);

        // 장바구니 아이템 응답 객체 생성
        return CartItemResponse.from(cartItem, productImageUrl);
    }

    /**
     * 사용자 ID로 장바구니를 조회하는 메서드
     * @param userId 사용자 ID
     * @return 장바구니 응답 객체
     */
    @Transactional
    public CartResponse getCartByUserId(Integer userId) {
        // 사용자 ID로 장바구니 조회
        CartEntity cart = cartRepository.findByUserId(userId)
            .orElseThrow(() -> new NotFoundException("Cart not found"));

        // 장바구니 아이템 응답 리스트 생성
        List<CartItemResponse> cartItems = cart.getCartItems().stream()
            .map(cartItem -> {
                String productImageUrl = cartItem.getProduct().getImages().stream()
                    .findFirst()
                    .map(ProductImageEntity::getImageUrl)
                    .orElse(null);
                return CartItemResponse.from(cartItem, productImageUrl);
            })
            .collect(Collectors.toList());

        // 장바구니 응답 객체 생성
        return CartResponse.from(cart, cartItems);
    }

    /**
     * 장바구니에서 아이템을 제거하는 메서드
     * @param userId 사용자 ID
     * @param cartItemId 장바구니 아이템 ID
     * @return 제거된 장바구니 아이템 응답 객체
     */
    @Transactional
    public CartItemResponse removeCartItem(Integer userId, Integer cartItemId) {
        // 사용자 ID로 장바구니 조회
        CartEntity cart = cartRepository.findByUserId(userId)
            .orElseThrow(() -> new NotFoundException("Cart not found"));

        // 장바구니 아이템 ID로 아이템 조회
        CartItemEntity cartItem = cartItemRepository.findById(cartItemId)
            .orElseThrow(() -> new NotFoundException("Cart item not found"));

        // 장바구니 아이템이 해당 사용자의 장바구니에 속하는지 확인
        if (!cart.getId().equals(cartItem.getCart().getId())) {
            throw new NotFoundException("Cart item does not belong to the user's cart");
        }

        // 장바구니 아이템을 리스트에서 제거하고 삭제
        cart.getCartItems().remove(cartItem);
        cartItemRepository.delete(cartItem);

        // 장바구니의 총 가격을 업데이트
        updateCartTotalPrice(cart);

        // 상품 이미지 URL을 조회
        String productImageUrl = cartItem.getProduct().getImages().stream()
            .findFirst()
            .map(ProductImageEntity::getImageUrl)
            .orElse(null);

        // 제거된 장바구니 아이템 응답 객체 생성
        return CartItemResponse.from(cartItem, productImageUrl);
    }

    /**
     * 장바구니의 총 가격을 업데이트하는 메서드
     * @param cart 장바구니 엔티티
     */
    private void updateCartTotalPrice(CartEntity cart) {
        // 장바구니의 총 가격 계산
        BigDecimal totalPrice = cart.getCartItems().stream()
            .map(CartItemEntity::getTotalPrice)
            .reduce(BigDecimal.ZERO, BigDecimal::add);

        // 총 가격 설정 후 장바구니 저장
        cart.setTotalPrice(totalPrice);
        cartRepository.save(cart);
    }
}
