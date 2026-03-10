package com.flipkart.flipkartapplication.repository;

import com.flipkart.flipkartapplication.models.Order;
import com.flipkart.flipkartapplication.models.OrderStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Repository
public interface OrderRepository extends JpaRepository<Order, UUID> {

    // Get all orders for a specific user — used in getOrdersByUser
    List<Order> findByUserId(UUID userId);

    // Get orders for a user filtered by status
    // e.g. show only PENDING or DELIVERED orders
    List<Order> findByUserIdAndStatus(UUID userId, OrderStatus status);

    // Get all orders with a specific status — useful for admin dashboard
    List<Order> findByStatus(OrderStatus status);

    // Get orders placed within a date range — useful for admin reporting
    List<Order> findByCreatedAtBetween(LocalDateTime from, LocalDateTime to);

    // Count total orders placed by a user
    long countByUserId(UUID userId);
}