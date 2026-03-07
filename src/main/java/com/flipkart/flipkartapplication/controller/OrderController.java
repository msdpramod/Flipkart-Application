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

    // CREATE / PLACE ORDER
    @PostMapping("/user/{userId}")
    public ResponseEntity<OrderResponseDto> placeOrder(
            @PathVariable UUID userId,
            @Valid @RequestBody OrderRequestDto orderRequestDto) {

        OrderResponseDto response = orderService.placeOrder(userId, orderRequestDto);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    // GET ALL ORDERS
    @GetMapping
    public ResponseEntity<List<OrderResponseDto>> getAllOrders() {
        List<OrderResponseDto> orders = orderService.getAllOrders();
        return ResponseEntity.ok(orders);
    }

    // GET ORDERS BY USER
    @GetMapping("/user/{userId}")
    public ResponseEntity<List<OrderResponseDto>> getOrdersByUser(@PathVariable UUID userId) {
        List<OrderResponseDto> orders = orderService.getOrdersByUser(userId);
        return ResponseEntity.ok(orders);
    }

    // GET ORDER BY ID
    @GetMapping("/{orderId}")
    public ResponseEntity<OrderResponseDto> getOrderById(@PathVariable UUID orderId) {
        OrderResponseDto response = orderService.getOrderById(orderId);
        return ResponseEntity.ok(response);
    }

    // UPDATE ORDER STATUS
    @PutMapping("/{orderId}/status")
    public ResponseEntity<OrderResponseDto> updateOrderStatus(
            @PathVariable UUID orderId,
            @RequestParam String status) {

        OrderResponseDto response = orderService.updateOrderStatus(orderId, status);
        return ResponseEntity.ok(response);
    }
}