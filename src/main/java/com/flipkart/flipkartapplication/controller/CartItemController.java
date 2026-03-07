package com.flipkart.flipkartapplication.controller;

import com.flipkart.flipkartapplication.DTOs.CartItemRequestDto;
import com.flipkart.flipkartapplication.DTOs.CartItemResponseDto;
import com.flipkart.flipkartapplication.service.CartItemService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/cart-items")
@RequiredArgsConstructor
public class CartItemController {

    private final CartItemService cartItemService;

    // Add item to cart
    @PostMapping("/{userId}")
    public ResponseEntity<CartItemResponseDto> addItem(
            @PathVariable UUID userId,
            @Valid @RequestBody CartItemRequestDto requestDto) {
        CartItemResponseDto response = cartItemService.addItem(userId, requestDto);
        return ResponseEntity.status(201).body(response);
    }

    // Update cart item quantity
    @PutMapping("/{userId}/{cartItemId}")
    public ResponseEntity<CartItemResponseDto> updateItem(
            @PathVariable UUID userId,
            @PathVariable UUID cartItemId,
            @RequestParam int quantity) {
        CartItemResponseDto response = cartItemService.updateItem(userId, cartItemId, quantity);
        return ResponseEntity.ok(response);
    }

    // Remove item from cart
    @DeleteMapping("/{userId}/{cartItemId}")
    public ResponseEntity<Void> removeItem(
            @PathVariable UUID userId,
            @PathVariable UUID cartItemId) {
        boolean removed = cartItemService.removeItem(userId, cartItemId);
        if (removed) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
