package com.flipkart.flipkartapplication.models;

import jakarta.persistence.*;
import org.hibernate.annotations.UuidGenerator;

import java.util.UUID;

@Entity
@Table(name= "addresses")
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
