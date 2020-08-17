package com.example.ajax.controller;

import com.example.ajax.model.User;
import com.example.ajax.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
public class UserController {
    private UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/about")
    public ResponseEntity<User> indexGet(Authentication authentication) {
        User authUser = (User) authentication.getPrincipal();
        return new ResponseEntity<>(authUser, HttpStatus.OK);
    }
    @GetMapping("/get/userpage")
    public User getRole(Authentication authentication) {
        User authUser = (User) authentication.getPrincipal();
        return authUser;
    }
}
