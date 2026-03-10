package com.flipkart.flipkartapplication.mapper;

import com.flipkart.flipkartapplication.DTOs.ProductRequestDto;
import com.flipkart.flipkartapplication.DTOs.ProductResponseDto;
import com.flipkart.flipkartapplication.models.Product;

public class ProductMapper {

    public static ProductResponseDto toResponseDto(Product product) {
        return ProductResponseDto.builder()
                .id(product.getId())
                .name(product.getName())
                .description(product.getDescription())
                .price(product.getPrice())
                .stockQuantity(product.getStockQuantity())
                .category(product.getCategory())
                .imageUrl(product.getImageUrl())
                .isActive(product.isActive())
                .createdAt(product.getCreatedAt())
                .updatedAt(product.getUpdatedAt())
                .build();
    }

    public static Product toEntity(ProductRequestDto dto) {
        return Product.builder()
                .name(dto.getName())
                .description(dto.getDescription())
                .price(dto.getPrice())
                .stockQuantity(dto.getStockQuantity())
                .category(dto.getCategory())
                .imageUrl(dto.getImageUrl())
                .isActive(true)
                .build();
    }
}
