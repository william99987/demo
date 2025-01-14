package com.example.demo.controller;

import com.example.demo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.example.demo.entity.User;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.stereotype.Controller;

@Controller
//@RequestMapping("/demo-0.0.1/user")
@CrossOrigin(origins = "http://10.30.54.23:3000")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/register")
    public ResponseEntity<String> userRegister(@RequestBody User request) {
        try {
            System.out.println("Hi User!!!!");
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
