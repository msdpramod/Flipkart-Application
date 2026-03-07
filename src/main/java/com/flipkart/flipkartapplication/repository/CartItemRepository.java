package com.flipkart.flipkartapplication.repository;


import com.flipkart.flipkartapplication.models.CartItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface CartItemRepository extends JpaRepository<CartItem, UUID> {
    // Optional: find all items by cart id
    List<CartItem> findByCartId(UUID cartId);
}