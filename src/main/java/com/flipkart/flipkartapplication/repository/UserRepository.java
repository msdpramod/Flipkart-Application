package com.flipkart.flipkartapplication.repository;

import com.flipkart.flipkartapplication.models.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface UserRepository extends JpaRepository<User, UUID> {

    //you can extends to CrudRepository or JpaRepository, both are same but JpaRepository has some extra methods like findAll, findById, save, deleteById etc.


}
