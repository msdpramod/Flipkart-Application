package com.flipkart.flipkartapplication.DTOs;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OrderRequestDto {

    private String orderNotes;  // optional notes from user

    @NotNull(message = "Shipping address is required")
    @Valid
    private AddressDto shippingAddress;  // where to deliver the order
}