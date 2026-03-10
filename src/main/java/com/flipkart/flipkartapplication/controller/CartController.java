package com.flipkart.flipkartapplication.controller;

import com.flipkart.flipkartapplication.DTOs.CartItemResponseDto;
import com.flipkart.flipkartapplication.service.CartService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/carts")
@RequiredArgsConstructor
public class CartController {

    private final CartService cartService;

    // Get all items in a user's cart
    @GetMapping("/{userId}")
    public ResponseEntity<List<CartItemResponseDto>> getCartItems(@PathVariable UUID userId) {
        List<CartItemResponseDto> items = cartService.getCartItems(userId);
        return ResponseEntity.ok(items);
    }

    // Clear user's cart
    @DeleteMapping("/{userId}/clear")
    public ResponseEntity<Void> clearCart(@PathVariable UUID userId) {
        cartService.clearCart(userId);
        return ResponseEntity.noContent().build();
    }
}