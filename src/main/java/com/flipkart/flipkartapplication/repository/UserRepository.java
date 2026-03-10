package com.flipkart.flipkartapplication.repository;

import com.flipkart.flipkartapplication.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface UserRepository extends JpaRepository<User, UUID> {

    // Used in createUser and updateUser to prevent duplicate emails
    boolean existsByEmail(String email);

    // Used in createUser and updateUser to prevent duplicate phone numbers
    boolean existsByPhone(String phone);

    // Useful for login / lookup by email
    Optional<User> findByEmail(String email);
}