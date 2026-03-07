package com.flipkart.flipkartapplication.service;


import com.flipkart.flipkartapplication.DTOs.CartItemRequestDto;
import com.flipkart.flipkartapplication.DTOs.CartItemResponseDto;

import java.util.UUID;

public interface CartItemService {

    // Add an item to user's cart
    CartItemResponseDto addItem(UUID userId, CartItemRequestDto requestDto);

    // Update item quantity
    CartItemResponseDto updateItem(UUID userId, UUID cartItemId, int quantity);

    // Remove item from cart
    boolean removeItem(UUID userId, UUID cartItemId);
}
