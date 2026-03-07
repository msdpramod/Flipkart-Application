package com.flipkart.flipkartapplication.repository;


import com.flipkart.flipkartapplication.models.Cart;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface CartRepository extends JpaRepository<Cart, UUID> {
    // Optional: find cart by user
    Cart findByUserId(UUID userId);
}
