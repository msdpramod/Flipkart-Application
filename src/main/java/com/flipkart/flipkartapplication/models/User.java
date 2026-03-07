package com.flipkart.flipkartapplication.models;

import jakarta.validation.constraints.*;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class User {

    private Long id;

    @NotBlank(message = "First name is required")
    private String firstName;

    private String lastName;

    @Email(message = "Invalid email format")
    private String email;

    @Pattern(regexp = "\\d{10}", message = "Phone must be 10 digits")
    private String phone;
}