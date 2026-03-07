package com.flipkart.flipkartapplication.service;

import com.flipkart.flipkartapplication.models.User;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserService {

    List<User> users= new ArrayList<>();

    public List<User> getAllUsers(){
        return users;
    }



    public List<User> createUser( User user){
        users.add(user);
        return users;
    }

}
