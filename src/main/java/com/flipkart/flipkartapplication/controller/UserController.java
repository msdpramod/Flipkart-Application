package com.flipkart.flipkartapplication.controller;

import com.flipkart.flipkartapplication.models.User;
import com.flipkart.flipkartapplication.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/flipkart")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping("/api/users")
    public List<User> getAllUsers(){
        return userService.getAllUsers();
    }


    @PostMapping("/api/users")
    public List<User> createUser(@Valid @RequestBody User user){
       return userService.createUser(user);
    }
}
