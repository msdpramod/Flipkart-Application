package com.flipkart.flipkartapplication.controller;

import com.flipkart.flipkartapplication.models.User;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/flipkart")
public class UserController {

    List<User> users= new ArrayList<>();
    @GetMapping("/api/users")
    public List<User> getAllUsers(){
        return users;
    }


    @PostMapping("/api/users")
    public List<User> createUser(@RequestBody User user){
        users.add(user);
        return users;
    }
}
