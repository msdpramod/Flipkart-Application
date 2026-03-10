package com.flipkart.flipkartapplication.DTOs;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CartItemResponseDto {

    private UUID id;           // cart item id
    private UUID productId;
    private String productName;
    private BigDecimal price;
    private int quantity;
    private BigDecimal totalPrice;  // price * quantity — computed field
    private boolean isActive;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}