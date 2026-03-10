package com.flipkart.flipkartapplication.DTOs;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OrderResponseDto {

    private UUID id;
    private UUID userId;
    private List<CartItemResponseDto> items;
    private Double totalAmount;
    private String status;          // PENDING, PROCESSING, SHIPPED, DELIVERED, CANCELLED
    private String orderNotes;
    private AddressDto shippingAddress;   // where order is being delivered
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}