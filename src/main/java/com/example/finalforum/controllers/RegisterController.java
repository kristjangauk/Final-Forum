package com.example.finalforum.controllers;


import com.example.finalforum.entities.User;
import com.example.finalforum.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.View;
import org.springframework.web.servlet.view.RedirectView;

import javax.servlet.http.HttpServletRequest;

import java.time.LocalDateTime;
import java.util.Objects;

@Controller
public class RegisterController {

    private final UserRepository userRepository;

    @Autowired
    public RegisterController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @GetMapping("register")
    public String displayRegister(Model model) {
        return "register";
    }

    @PostMapping("register")
    public View registerUser(@RequestParam("username") String username, @RequestParam("password") String password,
                             @RequestParam("introduction") String introduction, HttpServletRequest request) {
        String contextPath = request.getContextPath();
        User user = new User();
        if (userRepository.getUserByUsername(username) == null) {
            user.setUsername(username);
            // I know that it can be blank field, but I did it on purpose to find out about Optionals:
            user.setPassword(password);
            user.setCreatedDate(LocalDateTime.now());
            userRepository.save(user);
            return new RedirectView(contextPath + "/login");
        } else
            return new RedirectView(contextPath + "/register");
    }
}