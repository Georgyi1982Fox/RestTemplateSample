package com.example.ajax.controller;

import com.example.ajax.model.Role;
import com.example.ajax.model.User;
import com.example.ajax.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
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

    @Autowired(required = true)
    PasswordEncoder passwordEncoder;


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
        Set<Role> roles = new HashSet<>();

        if (user.getRoles().iterator().next().getName().contains("ADMIN")) {
                roles.add(userService.getRoleById(1L));
                roles.add(userService.getRoleById(2L));


            } else if (user.getRoles().iterator().next().getName().contains("USER")) {
              roles.add(userService.getRoleById(2L));
            }

        user.setRoles(roles);
        userService.addUser(user);
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<List<User>> deleteUserGet(@PathVariable(name = "id") long id) throws SQLException {
        userService.deleteUser(id);
        return new ResponseEntity<>(userService.listAllUsers(), HttpStatus.OK);
    }
    @PutMapping("/update")
    public ResponseEntity<List<User>> updateUserPost(@RequestBody User userFrom) throws SQLException {
        Set<Role> roles = new HashSet<>();
        try {
            if (userFrom.getRoles().iterator().next().getName().contains("ADMIN")) {
                roles.add(userService.getRoleById(1L));
                roles.add(userService.getRoleById(2L));
            } else if (userFrom.getRoles().iterator().next().getName().contains("USER")) {
                roles.add(userService.getRoleById(2L));
            }  else {
                roles.add(userService.getRoleById(1L));
                roles.add(userService.getRoleById(2L));
            }
        } catch (Exception e) {
            System.out.println(userFrom.getEmail() + " not make it admin");
        }
        userFrom.setRoles(roles);
        userService.updateUser(userFrom);

        return new ResponseEntity<>(userService.listAllUsers(), HttpStatus.OK);
    }
}
