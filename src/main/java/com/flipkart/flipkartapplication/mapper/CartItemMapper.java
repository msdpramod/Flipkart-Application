package com.flipkart.flipkartapplication.mapper;

import com.flipkart.flipkartapplication.DTOs.CartItemResponseDto;
import com.flipkart.flipkartapplication.models.CartItem;

import java.math.BigDecimal;

public class CartItemMapper {

    public static CartItemResponseDto toResponseDto(CartItem item) {
        BigDecimal totalPrice = item.getProduct().getPrice()
                .multiply(BigDecimal.valueOf(item.getQuantity()));

        return CartItemResponseDto.builder()
                .id(item.getId())
                .productId(item.getProduct().getId())
                .productName(item.getProduct().getName())
                .price(item.getProduct().getPrice())
                .quantity(item.getQuantity())
                .totalPrice(totalPrice)
                .isActive(item.isActive())
                .createdAt(item.getCreatedAt())
                .updatedAt(item.getUpdatedAt())
                .build();
    }
}
