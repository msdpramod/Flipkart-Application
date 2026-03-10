package com.flipkart.flipkartapplication.repository;

import com.flipkart.flipkartapplication.models.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

@Repository
public interface ProductRepository extends JpaRepository<Product, UUID> {

    // Used in getAllProducts — only return active products to the public
    List<Product> findByIsActiveTrue();

    // Filter products by category — useful for browsing
    List<Product> findByCategoryAndIsActiveTrue(String category);

    // Search products by name (case-insensitive) — useful for search bar
    List<Product> findByNameContainingIgnoreCaseAndIsActiveTrue(String name);

    // Filter products within a price range
    List<Product> findByPriceBetweenAndIsActiveTrue(BigDecimal minPrice, BigDecimal maxPrice);

    // Check if product name already exists — useful for preventing duplicates
    boolean existsByNameIgnoreCase(String name);
}