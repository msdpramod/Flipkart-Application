package com.flipkart.flipkartapplication.DTOs;


import lombok.Data;

@Data
public class UserResponseDto {
    private String firstName;

    private String lastName;

    private String email;

    private String phone;

    private AddressDto addressdto;
}
