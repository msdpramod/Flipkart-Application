package com.flipkart.flipkartapplication.repository;

import com.flipkart.flipkartapplication.models.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface ProductRepository extends JpaRepository<Product, UUID> {
}
