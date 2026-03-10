package com.flipkart.flipkartapplication.service;

import com.flipkart.flipkartapplication.DTOs.CartItemRequestDto;
import com.flipkart.flipkartapplication.DTOs.CartItemResponseDto;

import java.util.UUID;

public interface CartItemService {

    // Add an item to the user's cart (increments quantity if product already exists)
    CartItemResponseDto addItem(UUID userId, CartItemRequestDto requestDto);

    // Update the quantity of an existing cart item
    CartItemResponseDto updateItem(UUID userId, UUID cartItemId, int quantity);

    // Remove an item from the cart
    boolean removeItem(UUID userId, UUID cartItemId);
}