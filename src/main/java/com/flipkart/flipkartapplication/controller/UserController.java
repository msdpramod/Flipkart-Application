package com.flipkart.flipkartapplication.controller;

import com.flipkart.flipkartapplication.DTOs.UserRequestDto;
import com.flipkart.flipkartapplication.DTOs.UserResponseDto;
import com.flipkart.flipkartapplication.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/flipkart/api/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    // GET all users
    @GetMapping
    public ResponseEntity<List<UserResponseDto>> getAllUsers() {

        List<UserResponseDto> users = userService.getAllUsers();

        if (users.isEmpty()) {
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.ok(users);
    }

    // GET user by ID
    @GetMapping("/{id}")
    public ResponseEntity<UserResponseDto> getUserById(@PathVariable UUID id) {

        Optional<UserResponseDto> user = userService.findUserById(id);

        if (user.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(user.get());
    }

    // CREATE user
    @PostMapping
    public ResponseEntity<UserResponseDto> createUser(@Valid @RequestBody UserRequestDto user) {

        UserResponseDto createdUser = userService.createUser(user);

        return ResponseEntity.status(HttpStatus.CREATED).body(createdUser);
    }

    // UPDATE user
    @PutMapping("/{id}")
    public ResponseEntity<UserResponseDto> updateUser(
            @PathVariable UUID id,
            @Valid @RequestBody UserRequestDto updatedUser) {

        Optional<UserResponseDto> user = userService.updateUser(id, updatedUser);

        if (user.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(user.get());
    }

    // DELETE user
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable UUID id) {

        boolean deleted = userService.deleteUser(id);

        if (!deleted) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.noContent().build();
    }
}