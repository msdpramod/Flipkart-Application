package com.flipkart.flipkartapplication.controller;

import org.apache.catalina.User;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
}
