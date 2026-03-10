package com.flipkart.flipkartapplication.controller;

import com.flipkart.flipkartapplication.DTOs.OrderRequestDto;
import com.flipkart.flipkartapplication.DTOs.OrderResponseDto;
import com.flipkart.flipkartapplication.service.OrderService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/orders")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;

    // Place a new order
    @PostMapping("/user/{userId}")
    public ResponseEntity<OrderResponseDto> placeOrder(
            @PathVariable UUID userId,
            @Valid @RequestBody OrderRequestDto orderRequestDto) {
        OrderResponseDto response = orderService.placeOrder(userId, orderRequestDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    // Get all orders (admin use)
    @GetMapping
    public ResponseEntity<List<OrderResponseDto>> getAllOrders() {
        List<OrderResponseDto> orders = orderService.getAllOrders();
        return ResponseEntity.ok(orders);
    }

    // Get orders for a specific user
    @GetMapping("/user/{userId}")
    public ResponseEntity<List<OrderResponseDto>> getOrdersByUser(@PathVariable UUID userId) {
        List<OrderResponseDto> orders = orderService.getOrdersByUser(userId);
        return ResponseEntity.ok(orders);
    }

    // Get a single order by ID
    @GetMapping("/{orderId}")
    public ResponseEntity<OrderResponseDto> getOrderById(@PathVariable UUID orderId) {
        OrderResponseDto response = orderService.getOrderById(orderId);
        return ResponseEntity.ok(response);
    }

    // Update order status
    @PatchMapping("/{orderId}/status")
    public ResponseEntity<OrderResponseDto> updateOrderStatus(
            @PathVariable UUID orderId,
            @RequestParam String status) {
        OrderResponseDto response = orderService.updateOrderStatus(orderId, status);
        return ResponseEntity.ok(response);
    }
}