package com.flipkart.flipkartapplication.DTOs;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CartResponseDto {

    private UUID cartId;
    private UUID userId;
    private List<CartItemResponseDto> items;
    private int totalItems;          // total number of items
    private BigDecimal cartTotal;    // sum of all item totalPrices
}