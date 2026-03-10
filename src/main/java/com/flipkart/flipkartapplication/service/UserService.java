package com.flipkart.flipkartapplication.service;

import com.flipkart.flipkartapplication.DTOs.UserRequestDto;
import com.flipkart.flipkartapplication.DTOs.UserResponseDto;

import java.util.List;
import java.util.UUID;

public interface UserService {

    // Get all users
    List<UserResponseDto> getAllUsers();

    // Find user by ID
    UserResponseDto findUserById(UUID id);

    // Create a new user
    UserResponseDto createUser(UserRequestDto requestDto);

    // Update existing user
    UserResponseDto updateUser(UUID id, UserRequestDto requestDto);

    // Delete user by ID
    void deleteUser(UUID id);
}