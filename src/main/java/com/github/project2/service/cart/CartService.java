package com.github.project2.service.cart;

import com.github.project2.dto.cart.CartItemRequest;
import com.github.project2.dto.cart.CartItemResponse;
import com.github.project2.dto.cart.CartResponse;
import com.github.project2.entity.cart.CartEntity;
import com.github.project2.entity.cart.CartItemEntity;
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

    @Transactional
    public CartItemResponse addToCart(Integer userId, CartItemRequest cartItemRequest) {
        UserEntity user = userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException("User not found"));

        CartEntity cart = cartRepository.findByUserId(userId)
                .orElseGet(() -> {
                    CartEntity newCart = new CartEntity();
                    newCart.setUser(user);
                    return cartRepository.save(newCart);
                });

        // ProductSizeEntity 조회를 ProductId와 Size로 조회합니다.
        ProductSizeEntity productSize = productSizeRepository.findByProductIdAndSize(cartItemRequest.getProductId(), cartItemRequest.getSize())
                .orElseThrow(() -> new NotFoundException("Product size not found"));

        if (productSize.getSizeStock() < cartItemRequest.getQuantity()) {
            throw new NotFoundException("Product size out of stock");
        }

        CartItemEntity cartItem = CartItemEntity.builder()
                .cart(cart)
                .product(productSize.getProduct())
                .quantity(cartItemRequest.getQuantity())
                .price(productSize.getProduct().getPrice())
                .totalPrice(productSize.getProduct().getPrice().multiply(BigDecimal.valueOf(cartItemRequest.getQuantity())))
                .build();

        cartItemRepository.save(cartItem);

        // CartEntity의 총 가격을 업데이트합니다.
        updateCartTotalPrice(cart);

        return toCartItemResponse(cartItem);
    }

    @Transactional
    public CartResponse getCartByUserId(Integer userId) {
        CartEntity cart = cartRepository.findByUserId(userId)
                .orElseThrow(() -> new NotFoundException("Cart not found"));

        List<CartItemResponse> cartItems = cart.getCartItems().stream()
                .map(this::toCartItemResponse)
                .collect(Collectors.toList());

        return CartResponse.builder()
                .id(cart.getId())
                .userId(cart.getUser().getId())
                .items(cartItems)
                .totalPrice(cartItems.stream()
                        .map(item -> item.getPrice().multiply(BigDecimal.valueOf(item.getQuantity())))
                        .reduce(BigDecimal.ZERO, BigDecimal::add))
                .build();

    }

    @Transactional
    public void removeCartItem(Integer userId, Integer cartItemId) {
        CartEntity cart = cartRepository.findByUserId(userId)
                .orElseThrow(() -> new NotFoundException("Cart not found"));

        CartItemEntity cartItem = cartItemRepository.findById(cartItemId)
                .orElseThrow(() -> new NotFoundException("Cart item not found"));

        if (!cart.getId().equals(cartItem.getCart().getId())) {
            throw new NotFoundException("Cart item does not belong to the user's cart");
        }

        cartItemRepository.delete(cartItem);

        updateCartTotalPrice(cart);
    }

    private void updateCartTotalPrice(CartEntity cart) {
        BigDecimal totalPrice = cart.getCartItems().stream()
                .map(CartItemEntity::getTotalPrice)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        cart.setTotalPrice(totalPrice);
        cartRepository.save(cart);  // CartEntity의 변경 사항을 저장합니다.
    }

    private CartItemResponse toCartItemResponse(CartItemEntity cartItem) {
        return CartItemResponse.builder()
                .id(cartItem.getId())
                .productId(cartItem.getProduct().getId())
                .quantity(cartItem.getQuantity())
                .price(cartItem.getPrice())
                .totalPrice(cartItem.getTotalPrice())
                .build();
    }
}
