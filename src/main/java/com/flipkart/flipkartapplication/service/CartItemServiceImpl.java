package com.flipkart.flipkartapplication.service;

import com.flipkart.flipkartapplication.DTOs.CartItemRequestDto;
import com.flipkart.flipkartapplication.DTOs.CartItemResponseDto;
import com.flipkart.flipkartapplication.exception.BadRequestException;
import com.flipkart.flipkartapplication.exception.ResourceNotFoundException;
import com.flipkart.flipkartapplication.mapper.CartItemMapper;
import com.flipkart.flipkartapplication.models.Cart;
import com.flipkart.flipkartapplication.models.CartItem;
import com.flipkart.flipkartapplication.models.Product;
import com.flipkart.flipkartapplication.models.User;
import com.flipkart.flipkartapplication.repository.CartItemRepository;
import com.flipkart.flipkartapplication.repository.CartRepository;
import com.flipkart.flipkartapplication.repository.ProductRepository;
import com.flipkart.flipkartapplication.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

        // 1. Fetch user
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + userId));

        // 2. Fetch product and check if it's active and in stock
        Product product = productRepository.findById(requestDto.getProductId())
                .orElseThrow(() -> new ResourceNotFoundException("Product not found with id: " + requestDto.getProductId()));

        if (!product.isActive()) {
            throw new BadRequestException("Product is no longer available: " + product.getName());
        }

        if (product.getStockQuantity() < requestDto.getQuantity()) {
            throw new BadRequestException("Insufficient stock for product: " + product.getName()
                    + ". Available: " + product.getStockQuantity());
        }

        // 3. Get or create cart for user
        Cart cart = user.getCart();
        if (cart == null) {
            cart = Cart.builder()
                    .user(user)
                    .isActive(true)
                    .build();
            cart = cartRepository.save(cart);
        }

        // 4. If product already exists in cart, increment quantity
        Optional<CartItem> existingItem = cart.getItems().stream()
                .filter(item -> item.getProduct().getId().equals(product.getId()))
                .findFirst();

        CartItem cartItem;
        if (existingItem.isPresent()) {
            cartItem = existingItem.get();
            int newQuantity = cartItem.getQuantity() + requestDto.getQuantity();

            // check stock again against updated total quantity
            if (product.getStockQuantity() < newQuantity) {
                throw new BadRequestException("Insufficient stock. Requested total: " + newQuantity
                        + ", Available: " + product.getStockQuantity());
            }

            cartItem.setQuantity(newQuantity);
        } else {
            cartItem = CartItem.builder()
                    .product(product)
                    .quantity(requestDto.getQuantity())
                    .isActive(true)
                    .build();
            cart.addItem(cartItem);  // uses helper method — sets cart on item
        }

        cartRepository.save(cart);
        return CartItemMapper.toResponseDto(cartItem);
    }

    @Override
    public CartItemResponseDto updateItem(UUID userId, UUID cartItemId, int quantity) {

        // 1. Validate user exists
        userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + userId));

        // 2. Fetch cart item
        CartItem cartItem = cartItemRepository.findById(cartItemId)
                .orElseThrow(() -> new ResourceNotFoundException("Cart item not found with id: " + cartItemId));

        // 3. Ensure cart item belongs to this user
        if (!cartItem.getCart().getUser().getId().equals(userId)) {
            throw new BadRequestException("Cart item does not belong to this user");
        }

        // 4. Check stock availability for new quantity
        Product product = cartItem.getProduct();
        if (product.getStockQuantity() < quantity) {
            throw new BadRequestException("Insufficient stock for product: " + product.getName()
                    + ". Available: " + product.getStockQuantity());
        }

        cartItem.setQuantity(quantity);
        CartItem updated = cartItemRepository.save(cartItem);
        return CartItemMapper.toResponseDto(updated);
    }

    @Override
    public boolean removeItem(UUID userId, UUID cartItemId) {

        // 1. Validate user exists
        userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + userId));

        // 2. Fetch cart item
        CartItem cartItem = cartItemRepository.findById(cartItemId)
                .orElseThrow(() -> new ResourceNotFoundException("Cart item not found with id: " + cartItemId));

        // 3. Ensure cart item belongs to this user
        if (!cartItem.getCart().getUser().getId().equals(userId)) {
            throw new BadRequestException("Cart item does not belong to this user");
        }

        // 4. Remove via helper to keep both sides of relationship in sync
        Cart cart = cartItem.getCart();
        cart.removeItem(cartItem);
        cartRepository.save(cart);

        return true;
    }
}