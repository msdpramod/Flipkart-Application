package com.flipkart.flipkartapplication.controller;

import com.flipkart.flipkartapplication.models.User;
import com.flipkart.flipkartapplication.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/flipkart")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    @GetMapping("/api/users")
    public ResponseEntity<List<User>> getAllUsers() {

        List<User> users = userService.getAllUsers();

        if (users == null || users.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(users);
    }

    @GetMapping("/api/users/{id}")
    public ResponseEntity<User> getUserById(@PathVariable String id) {

        User user = userService.findUserById(UUID.fromString(id));
        if (user == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(user);
    }

    @PostMapping("/api/users")
    public ResponseEntity<List<User>> createUser(@Valid @RequestBody User user) {

        List<User> createdUser = userService.createUser(user);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdUser);
    }
}
