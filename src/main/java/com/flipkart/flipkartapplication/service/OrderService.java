package com.flipkart.flipkartapplication.service;

import com.flipkart.flipkartapplication.DTOs.OrderRequestDto;
import com.flipkart.flipkartapplication.DTOs.OrderResponseDto;

import java.util.List;
import java.util.UUID;

public interface OrderService {

    // Place a new order from user's current cart
    OrderResponseDto placeOrder(UUID userId, OrderRequestDto orderRequestDto);

    // Get all orders (admin)
    List<OrderResponseDto> getAllOrders();

    // Get all orders for a specific user
    List<OrderResponseDto> getOrdersByUser(UUID userId);

    // Get a single order by ID
    OrderResponseDto getOrderById(UUID orderId);

    // Update order status
    OrderResponseDto updateOrderStatus(UUID orderId, String status);
}