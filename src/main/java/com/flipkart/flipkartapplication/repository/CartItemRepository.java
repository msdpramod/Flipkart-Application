package com.flipkart.flipkartapplication.repository;

import com.flipkart.flipkartapplication.models.CartItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface CartItemRepository extends JpaRepository<CartItem, UUID> {

    // Get all active items in a specific cart
    List<CartItem> findByCartId(UUID cartId);

    // Find a specific product inside a specific cart
    // Used in addItem to check if product already exists before adding
    Optional<CartItem> findByCartIdAndProductId(UUID cartId, UUID productId);

    // Delete all items in a cart — used during clearCart
    void deleteByCartId(UUID cartId);
}