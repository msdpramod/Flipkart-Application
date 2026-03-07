package com.flipkart.flipkartapplication.service;


import com.flipkart.flipkartapplication.DTOs.CartItemRequestDto;
import com.flipkart.flipkartapplication.DTOs.CartItemResponseDto;
import com.flipkart.flipkartapplication.models.Cart;
import com.flipkart.flipkartapplication.models.CartItem;
import com.flipkart.flipkartapplication.models.Product;
import com.flipkart.flipkartapplication.models.User;
import com.flipkart.flipkartapplication.repository.CartItemRepository;
import com.flipkart.flipkartapplication.repository.CartRepository;
import com.flipkart.flipkartapplication.repository.ProductRepository;
import com.flipkart.flipkartapplication.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional
public class CartItemServiceImpl implements CartItemService {

    private final UserRepository userRepository;
    private final ProductRepository productRepository;
    private final CartRepository cartRepository;
    private final CartItemRepository cartItemRepository;

    @Override
    public CartItemResponseDto addItem(UUID userId, CartItemRequestDto requestDto) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        Cart cart = user.getCart();
        if (cart == null) {
            cart = new Cart();
            cart.setUser(user);
            cartRepository.save(cart);
        }

        Product product = productRepository.findById(requestDto.getProductId())
                .orElseThrow(() -> new RuntimeException("Product not found"));

        CartItem cartItem = null;
        if (cart.getItems() != null) {
            for (CartItem item : cart.getItems()) {
                if (item.getProduct().getId().equals(product.getId())) {
                    cartItem = item;
                    break;
                }
            }
        }

        if (cartItem != null) {
            cartItem.setQuantity(cartItem.getQuantity() + requestDto.getQuantity());
        } else {
            cartItem = new CartItem();
            cartItem.setCart(cart);
            cartItem.setProduct(product);
            cartItem.setQuantity(requestDto.getQuantity());
            if (cart.getItems() != null) {
                cart.getItems().add(cartItem);
            }
        }

        CartItem savedItem = cartItemRepository.save(cartItem);
        return mapToResponseDto(savedItem); // ✅ use the static mapper instead of toResponseDto()
    }

    @Override
    public CartItemResponseDto updateItem(UUID userId, UUID cartItemId, int quantity) {
        CartItem cartItem = cartItemRepository.findById(cartItemId)
                .orElseThrow(() -> new RuntimeException("CartItem not found"));

        if (!cartItem.getCart().getUser().getId().equals(userId)) {
            throw new RuntimeException("CartItem does not belong to user");
        }

        cartItem.setQuantity(quantity);
        CartItem savedItem = cartItemRepository.save(cartItem);
        return mapToResponseDto(savedItem); // ✅ corrected here as well
    }

    @Override
    public boolean removeItem(UUID userId, UUID cartItemId) {
        Optional<CartItem> cartItemOpt = cartItemRepository.findById(cartItemId);
        if (cartItemOpt.isEmpty()) return false;

        CartItem cartItem = cartItemOpt.get();
        if (!cartItem.getCart().getUser().getId().equals(userId)) return false;

        if (cartItem.getCart().getItems() != null) {
            cartItem.getCart().getItems().remove(cartItem);
        }

        cartItemRepository.delete(cartItem);
        return true;
    }

    // -----------------------
    // Mapper helper
    // -----------------------
    public static CartItemResponseDto mapToResponseDto(CartItem item) {
        return CartItemResponseDto.builder()
                .id(item.getId())
                .productId(item.getProduct().getId())
                .productName(item.getProduct().getName())
                .price(item.getProduct().getPrice())
                .quantity(item.getQuantity())
                .isActive(item.isActive())
                .createdAt(item.getCreatedAt())
                .updatedAt(item.getUpdatedAt())
                .build();
    }
}