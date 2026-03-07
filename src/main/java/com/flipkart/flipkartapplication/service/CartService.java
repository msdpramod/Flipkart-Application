package com.flipkart.flipkartapplication.service;

import com.flipkart.flipkartapplication.DTOs.CartItemResponseDto;

import java.util.List;
import java.util.UUID;

public interface CartService {

    // Get all items in a user's cart
    List<CartItemResponseDto> getCartItems(UUID userId);

    // Clear cart
    boolean clearCart(UUID userId);
}