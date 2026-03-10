package com.flipkart.flipkartapplication.service;

import com.flipkart.flipkartapplication.DTOs.ProductRequestDto;
import com.flipkart.flipkartapplication.DTOs.ProductResponseDto;

import java.util.List;
import java.util.UUID;

public interface ProductService {

    // Create a new product
    ProductResponseDto createProduct(ProductRequestDto productRequestDto);

    // Get all active products
    List<ProductResponseDto> getAllProducts();

    // Get product by ID
    ProductResponseDto getProductById(UUID id);

    // Update product details
    ProductResponseDto updateProduct(UUID id, ProductRequestDto productRequestDto);

    // Soft delete — marks product inactive instead of removing from DB
    void deleteProduct(UUID id);
}