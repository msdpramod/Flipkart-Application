package com.flipkart.flipkartapplication.DTOs;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserResponseDto {

    private UUID id;
    private String firstName;
    private String lastName;
    private String email;
    private String phone;
    private String role;        // CUSTOMER, SELLER, ADMIN
    private AddressDto address;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}