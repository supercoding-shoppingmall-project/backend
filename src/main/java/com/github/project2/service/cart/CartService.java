package com.github.project2.service.cart;

import com.github.project2.dto.cart.CartItemRequest;
import com.github.project2.dto.cart.CartItemResponse;
import com.github.project2.dto.cart.CartResponse;
import com.github.project2.entity.cart.CartEntity;
import com.github.project2.entity.cart.CartItemEntity;
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
     * 사용자의 장바구니에 물품을 추가하는 메서드.
     *
     * @param userId           장바구니에 물품을 추가할 사용자 ID
     * @param cartItemRequest  추가할 물품 정보가 담긴 요청 본문
     * @return                 추가된 물품의 정보를 담은 응답 본문
     */
    @Transactional
    public CartItemResponse addToCart(Integer userId, CartItemRequest cartItemRequest) {
        // 사용자 정보를 조회합니다.
        UserEntity user = userRepository.findById(userId)
            .orElseThrow(() -> new NotFoundException("User not found"));

        // 사용자의 장바구니를 조회하거나, 없으면 새로 생성합니다.
        CartEntity cart = cartRepository.findByUserId(userId)
            .orElseGet(() -> {
                CartEntity newCart = CartEntity.create(user);
                return cartRepository.save(newCart); // CartEntity를 먼저 저장합니다.
            });

        // CartEntity의 cartItems가 초기화되지 않은 경우 초기화
        if (cart.getCartItems() == null) {
            cart.setCartItems(new ArrayList<>());
        }

        // ProductSizeEntity를 ProductId와 Size로 조회합니다.
        ProductSizeEntity productSize = productSizeRepository.findByProductIdAndSize(cartItemRequest.getProductId(), cartItemRequest.getSize())
            .orElseThrow(() -> new NotFoundException("Product size not found"));

        // 재고가 충분하지 않은 경우 예외를 발생시킵니다.
        if (productSize.getSizeStock() < cartItemRequest.getQuantity()) {
            throw new NotFoundException("Product size out of stock");
        }

        // CartItemEntity를 생성합니다.
        CartItemEntity cartItem = CartItemEntity.create(cart, productSize.getProduct(), cartItemRequest.getQuantity(), productSize.getProduct().getPrice(), userId);

        // CartItemEntity를 저장합니다.
        cartItemRepository.save(cartItem);

        // CartEntity의 cartItems에 새로운 CartItem을 추가
        cart.getCartItems().add(cartItem);

        // CartEntity의 총 가격을 업데이트합니다.
        updateCartTotalPrice(cart);

        // 물품의 첫 번째 이미지를 가져옵니다.
        String productImageUrl = cartItem.getProduct().getImages().stream()
            .findFirst()
            .map(ProductImageEntity::getImageUrl)
            .orElse(null);

        // CartItemResponse를 반환합니다.
        return CartItemResponse.from(cartItem, productImageUrl);
    }

    /**
     * 특정 사용자의 장바구니를 조회하는 메서드.
     *
     * @param userId  장바구니를 조회할 사용자 ID
     * @return        사용자의 장바구니 정보를 담은 응답 본문
     */
    @Transactional
    public CartResponse getCartByUserId(Integer userId) {
        // 사용자의 장바구니를 조회합니다.
        CartEntity cart = cartRepository.findByUserId(userId)
            .orElseThrow(() -> new NotFoundException("Cart not found"));

        // 장바구니의 물품들을 CartItemResponse로 변환합니다.
        List<CartItemResponse> cartItems = cart.getCartItems().stream()
            .map(cartItem -> {
                String productImageUrl = cartItem.getProduct().getImages().stream()
                    .findFirst()
                    .map(ProductImageEntity::getImageUrl)
                    .orElse(null);
                return CartItemResponse.from(cartItem, productImageUrl);
            })
            .collect(Collectors.toList());

        // CartResponse를 반환합니다.
        return CartResponse.from(cart, cartItems);
    }

    /**
     * 장바구니에서 특정 물품을 삭제하는 메서드.
     *
     * @param userId      장바구니에서 물품을 삭제할 사용자 ID
     * @param cartItemId  삭제할 물품의 ID
     * @return            삭제된 물품의 정보를 담은 응답 본문
     */
    @Transactional
    public CartItemResponse removeCartItem(Integer userId, Integer cartItemId) {
        // 사용자의 장바구니를 조회합니다.
        CartEntity cart = cartRepository.findByUserId(userId)
            .orElseThrow(() -> new NotFoundException("Cart not found"));

        // 장바구니 항목을 조회합니다.
        CartItemEntity cartItem = cartItemRepository.findById(cartItemId)
            .orElseThrow(() -> new NotFoundException("Cart item not found"));

        // 장바구니 항목이 사용자의 장바구니에 속해 있는지 확인합니다.
        if (!cart.getId().equals(cartItem.getCart().getId())) {
            throw new NotFoundException("Cart item does not belong to the user's cart");
        }

        // 장바구니 항목을 장바구니의 항목 리스트에서 제거합니다.
        cart.getCartItems().remove(cartItem);
        // 장바구니 항목을 삭제합니다.
        cartItemRepository.delete(cartItem);

        // CartEntity의 총 가격을 업데이트합니다.
        updateCartTotalPrice(cart);

        // 물품의 첫 번째 이미지를 가져옵니다.
        String productImageUrl = cartItem.getProduct().getImages().stream()
            .findFirst()
            .map(ProductImageEntity::getImageUrl)
            .orElse(null);

        // CartItemResponse를 반환합니다.
        return CartItemResponse.from(cartItem, productImageUrl);
    }

    /**
     * 장바구니의 총 가격을 업데이트하는 메서드.
     *
     * @param cart  총 가격을 업데이트할 장바구니
     */
    private void updateCartTotalPrice(CartEntity cart) {
        // 장바구니의 총 가격을 계산합니다.
        BigDecimal totalPrice = cart.getCartItems().stream()
            .map(CartItemEntity::getTotalPrice)
            .reduce(BigDecimal.ZERO, BigDecimal::add);

        // 장바구니의 총 가격을 설정합니다.
        cart.setTotalPrice(totalPrice);
        // 장바구니를 저장합니다.
        cartRepository.save(cart);
    }
}
