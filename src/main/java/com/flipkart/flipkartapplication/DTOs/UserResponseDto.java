package com.flipkart.flipkartapplication.DTOs;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserResponseDto {
    private UUID id;
    private String firstName;

    private String lastName;

    private String email;

    private String phone;

    private AddressDto addressdto;
}
