package com.flipkart.flipkartapplication.repository;

import com.flipkart.flipkartapplication.models.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface OrderRepository extends JpaRepository<Order, UUID> {

    // Fetch all orders for a specific user
    List<Order> findByUserId(UUID userId);
}