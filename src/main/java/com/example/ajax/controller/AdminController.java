package com.example.ajax.controller;

import com.example.ajax.model.Role;
import com.example.ajax.model.User;
import com.example.ajax.service.RolService;
import com.example.ajax.service.RoleService;
import com.example.ajax.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/admin/")
public class AdminController {
    private UserService userService;

    @Autowired
    BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    RolService roleService;

    @Autowired
    public AdminController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/allUsers")
    public ResponseEntity<List<User>> showUsers() throws SQLException {
        return new ResponseEntity<>(userService.listAllUsers(), HttpStatus.OK);
    }

    @GetMapping("/get/userRole")
    public ResponseEntity<User> getRole(Authentication authentication) {
        User authUser = (User) authentication.getPrincipal();
        return new ResponseEntity<>(authUser, HttpStatus.OK);
    }

    @PostMapping(value = "/addUser", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<User> addUser(@RequestBody User user) throws Exception {
        if (user.getPassword() == null) {
            return new ResponseEntity<>(HttpStatus.OK);
        } else
        userService.addUser(user);
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<List<User>> deleteUserGet(@PathVariable(name = "id") long id) throws SQLException {
        userService.deleteUser(id);
        return new ResponseEntity<>(userService.listAllUsers(), HttpStatus.OK);
    }

    @PutMapping("/update")
    public ResponseEntity<User> updateUserPost(@RequestBody User user) throws Exception {
        User us = userService.getUserById(user.getId());
        Set<Role> roles = new HashSet<>();

        if (user.getRoles().iterator().next().getName()==null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        if (user.getRoles().iterator().next().getName().contains("ADMIN")) {
            roles.add(roleService.getRoleById(1L));
        } else if (user.getRoles().iterator().next().getName().contains("USER")) {
            roles.add(roleService.getRoleById(2L));
        }
        if (!user.getPassword().equals(us.getPassword())) {
            user.setRoles(roles);
            user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
            userService.updateUser(user);
        } else if (user.getPassword().equals(us.getPassword())) {
            user.setRoles(roles);
            userService.updateUser(user);
        }
        return new ResponseEntity<>(user,HttpStatus.OK);
    }
}

