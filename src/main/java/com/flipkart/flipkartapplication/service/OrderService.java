package com.flipkart.flipkartapplication.service;

import com.flipkart.flipkartapplication.DTOs.OrderRequestDto;
import com.flipkart.flipkartapplication.DTOs.OrderResponseDto;

import java.util.List;
import java.util.UUID;

public interface OrderService {

    OrderResponseDto placeOrder(UUID userId, OrderRequestDto orderRequestDto);

    List<OrderResponseDto> getAllOrders();

    List<OrderResponseDto> getOrdersByUser(UUID userId);

    OrderResponseDto getOrderById(UUID orderId);

    OrderResponseDto updateOrderStatus(UUID orderId, String status);
}