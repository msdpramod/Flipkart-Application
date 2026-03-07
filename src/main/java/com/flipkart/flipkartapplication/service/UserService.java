package com.flipkart.flipkartapplication.service;

import com.flipkart.flipkartapplication.models.User;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

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

    public Optional<User> findUserById(UUID id) {

        for (User user : users) {
            if (user.getId().equals(id)) {
                return Optional.of(user);
            }
        }

        return Optional.empty();
    }
    public Optional<User> updateUser(UUID id, User updatedUser) {

        Optional<User> existingUser = findUserById(id);

        if (existingUser.isEmpty()) {
            return Optional.empty();
        }

        User user = existingUser.get();

        user.setFirstName(updatedUser.getFirstName());
        user.setLastName(updatedUser.getLastName());
        user.setEmail(updatedUser.getEmail());
        user.setPhone(updatedUser.getPhone());

        return Optional.of(user);
    }

}
