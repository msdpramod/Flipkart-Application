package com.flipkart.flipkartapplication.models;

import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
public class User {
    private Long id;
    private String firstName;
    private String lastName;
    private String email;
    private String phone;


}
