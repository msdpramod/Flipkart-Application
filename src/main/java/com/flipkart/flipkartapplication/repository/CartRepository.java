package com.flipkart.flipkartapplication.repository;

import com.flipkart.flipkartapplication.models.Cart;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface CartRepository extends JpaRepository<Cart, UUID> {

    // Fetch active cart for a user directly — avoids loading the User entity first
    Optional<Cart> findByUserIdAndIsActiveTrue(UUID userId);

    // Check if a user already has a cart
    boolean existsByUserId(UUID userId);
}