package com.flipkart.flipkartapplication.models;

import jakarta.validation.constraints.*;
import lombok.*;

import java.util.UUID;

@Data
@NoArgsConstructor
public class User {

    private UUID id = UUID.randomUUID();

    @NotBlank
    private String firstName;

    private String lastName;

    @Email
    private String email;

    @Pattern(regexp = "\\d{10}")
    private String phone;
}