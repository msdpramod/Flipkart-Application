package com.flipkart.flipkartapplication.controller;

import com.flipkart.flipkartapplication.models.User;
import com.flipkart.flipkartapplication.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/flipkart")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping("/api/users")
    public ResponseEntity<List<User>> getAllUsers() {
        return new ResponseEntity<>(userService.getAllUsers(), HttpStatus.OK);
    }

    @GetMapping("/api/users/{id}")
    public User getUserById(@PathVariable String id){
        return userService.findUserById(java.util.UUID.fromString(id));
    }

    @PostMapping("/api/users")
    public List<User> createUser(@Valid @RequestBody User user){
       return userService.createUser(user);
    }
}
