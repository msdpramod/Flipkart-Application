package com.flipkart.flipkartapplication.repository;

import com.flipkart.flipkartapplication.models.Address;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface AddressRepository extends JpaRepository<Address, UUID> {

    // Address is always managed via User (CascadeType.ALL + orphanRemoval = true)
    // so no custom queries are needed here.
    // This repo exists in case direct address lookups are needed in future.
}