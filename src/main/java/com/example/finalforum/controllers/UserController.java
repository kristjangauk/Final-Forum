package com.example.finalforum.controllers;

import com.example.finalforum.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;

public class UserController {

    private final UserRepository userRepository;

    @Autowired
    public UserController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }


}
