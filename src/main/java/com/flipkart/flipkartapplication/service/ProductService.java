package com.flipkart.flipkartapplication.service;

import com.flipkart.flipkartapplication.DTOs.ProductRequestDto;
import com.flipkart.flipkartapplication.DTOs.ProductResponseDto;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ProductService {

    // CREATE
    ProductResponseDto createProduct(ProductRequestDto productRequestDto);

    // READ ALL
    List<ProductResponseDto> getAllProducts();

    // READ BY ID
    Optional<ProductResponseDto> getProductById(UUID id);

    // UPDATE
    Optional<ProductResponseDto> updateProduct(UUID id, ProductRequestDto productRequestDto);

    // DELETE
    boolean deleteProduct(UUID id);
}