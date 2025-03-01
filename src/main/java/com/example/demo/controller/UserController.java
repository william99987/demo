package com.example.demo.controller;

import com.example.demo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.example.demo.entity.User;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.stereotype.Controller;

@RestController
//@RequestMapping("/demo-0.0.1/user")
@CrossOrigin(origins = "http://localhost:3000")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/bye")
    public String sayBye() {
        return "Bye";
    }

    @PostMapping("/register")
    public ResponseEntity<String> userRegister(@RequestBody User request) {
        try {
            System.out.println("Hi User!!!!");
            System.out.println(request.getUsername());
            userService.userRegister(request.getUsername(), request.getPassword(), request.getEmail());
            System.out.println("Fuck User!!!!");
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
