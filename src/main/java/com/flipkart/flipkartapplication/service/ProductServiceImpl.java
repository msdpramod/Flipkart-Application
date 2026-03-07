package com.flipkart.flipkartapplication.service;

import com.flipkart.flipkartapplication.DTOs.ProductRequestDto;
import com.flipkart.flipkartapplication.DTOs.ProductResponseDto;
import com.flipkart.flipkartapplication.models.Product;
import com.flipkart.flipkartapplication.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;

    // CREATE
    @Override
    public ProductResponseDto createProduct(ProductRequestDto dto) {
        Product product = mapToEntity(dto);
        Product saved = productRepository.save(product);
        return mapToDto(saved);
    }

    // READ ALL
    @Override
    public List<ProductResponseDto> getAllProducts() {
        List<Product> products = productRepository.findAll();
        List<ProductResponseDto> responseList = new ArrayList<>();
        for (Product product : products) {
            responseList.add(mapToDto(product));
        }
        return responseList;
    }

    // READ BY ID
    @Override
    public Optional<ProductResponseDto> getProductById(UUID id) {
        Optional<Product> productOpt = productRepository.findById(id);
        if (productOpt.isPresent()) {
            return Optional.of(mapToDto(productOpt.get()));
        } else {
            return Optional.empty();
        }
    }

    // UPDATE
    @Override
    public Optional<ProductResponseDto> updateProduct(UUID id, ProductRequestDto dto) {
        Optional<Product> productOpt = productRepository.findById(id);
        if (productOpt.isPresent()) {
            Product product = productOpt.get();
            product.setName(dto.getName());
            product.setDescription(dto.getDescription());
            product.setPrice(dto.getPrice());
            product.setStockQuantity(dto.getStockQuantity());
            product.setCategory(dto.getCategory());
            product.setImageUrl(dto.getImageUrl());
            Product updated = productRepository.save(product);
            return Optional.of(mapToDto(updated));
        }
        return Optional.empty();
    }

    // DELETE
    @Override
    public boolean deleteProduct(UUID id) {
        Optional<Product> productOpt = productRepository.findById(id);
        if (productOpt.isPresent()) {
            productRepository.delete(productOpt.get());
            return true;
        }
        return false;
    }

    // -----------------------
    // Mapper methods
    // -----------------------
    private ProductResponseDto mapToDto(Product product) {
        ProductResponseDto dto = new ProductResponseDto();
        dto.setId(product.getId());
        dto.setName(product.getName());
        dto.setDescription(product.getDescription());
        dto.setPrice(product.getPrice());
        dto.setStockQuantity(product.getStockQuantity());
        dto.setCategory(product.getCategory());
        dto.setImageUrl(product.getImageUrl());
        dto.setActive(product.isActive());
        dto.setCreatedAt(product.getCreatedAt());
        dto.setUpdatedAt(product.getUpdatedAt());
        return dto;
    }

    private Product mapToEntity(ProductRequestDto dto) {
        Product product = new Product();
        product.setName(dto.getName());
        product.setDescription(dto.getDescription());
        product.setPrice(dto.getPrice());
        product.setStockQuantity(dto.getStockQuantity());
        product.setCategory(dto.getCategory());
        product.setImageUrl(dto.getImageUrl());
        product.setActive(true);
        return product;
    }
}