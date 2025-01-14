package com.example.demo.controller;

import com.example.demo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.example.demo.entity.User;

@RestController
@RequestMapping("/user")
@CrossOrigin(origins = "http://localhost:3000")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/register")
    public ResponseEntity<String> userRegister(@RequestBody User request) {
        try {
            userService.userRegister(request.getUsername(), request.getPassword(), request.getEmail());
            return ResponseEntity.ok("User registration successful");
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping("/login")
    public ResponseEntity<String> userLogin(@RequestBody User request) {
        try {
            userService.userRegister(request.getUsername(), request.getPassword(), request.getEmail());
            return ResponseEntity.ok("User Login successful");
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
