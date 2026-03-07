package com.flipkart.flipkartapplication.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.UuidGenerator;

import java.util.UUID;

@Entity
@Table(name= "addresses")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Address {
    @Id
    @GeneratedValue
    @UuidGenerator
    private UUID id;
    private String street;
    private String city;
    private String state;
    private String country;
    private String zipcode;
}
