package com.flipkart.flipkartapplication.mapper;

import com.flipkart.flipkartapplication.DTOs.AddressDto;
import com.flipkart.flipkartapplication.DTOs.CartItemResponseDto;
import com.flipkart.flipkartapplication.DTOs.OrderResponseDto;
import com.flipkart.flipkartapplication.models.Address;
import com.flipkart.flipkartapplication.models.Order;
import com.flipkart.flipkartapplication.models.OrderItem;

import java.util.List;
import java.util.stream.Collectors;

public class OrderMapper {

    public static OrderResponseDto toResponseDto(Order order) {
        List<CartItemResponseDto> itemDtos = order.getItems()
                .stream()
                .map(OrderMapper::toCartItemResponseDto)
                .collect(Collectors.toList());

        return OrderResponseDto.builder()
                .id(order.getId())
                .userId(order.getUser().getId())
                .items(itemDtos)
                .totalAmount(order.getTotalAmount())
                .status(order.getStatus().name())
                .orderNotes(order.getOrderNotes())
                .shippingAddress(toAddressDto(order.getShippingAddress()))
                .createdAt(order.getCreatedAt())
                .updatedAt(order.getUpdatedAt())
                .build();
    }

    private static CartItemResponseDto toCartItemResponseDto(OrderItem item) {
        return CartItemResponseDto.builder()
                .productId(item.getProductId())
                .productName(item.getProductName())
                .price(item.getPriceAtPurchase())
                .quantity(item.getQuantity())
                .totalPrice(item.getPriceAtPurchase().multiply(
                        java.math.BigDecimal.valueOf(item.getQuantity())))
                .build();
    }

    private static AddressDto toAddressDto(Address address) {
        if (address == null) return null;
        return AddressDto.builder()
                .street(address.getStreet())
                .city(address.getCity())
                .state(address.getState())
                .country(address.getCountry())
                .zipcode(address.getZipcode())
                .build();
    }
}
