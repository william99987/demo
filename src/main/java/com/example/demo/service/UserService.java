package com.example.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public User userRegister(String username, String password, String email) {

        if (username == null || password == null || email == null) {
            throw new RuntimeException("Invalid input");
        }

        if (username.length() < 3 || username.length() > 20) {
            throw new RuntimeException("Username must be between 3 and 20 characters");
        }

        if (password.length() < 8 || password.length() > 20) {
            throw new RuntimeException("Password must be between 8 and 20 characters");
        }

        if (!email.matches("^[a-zA-Z0-9+_.-]+@[a-zA-Z0-9.-]+$")) {
            throw new RuntimeException("Invalid email");
        }

        if (!username.matches("^[a-zA-Z0-9]+$")) {
            throw new RuntimeException("Username must contain only alphanumeric characters");
        }

        if (userRepository.findByUsername(username).isPresent()) {
            throw new RuntimeException("Username already exists");
        }

        String encodedPassword = passwordEncode.encode(password);
        User user = new User(username, encodedPassword, email);
        return userRepository.save(user);
    }

    public boolean userLogin(String username, String email, String password) {
        Optional<User> user = userRepository.findByUsername(username);
        if (user.isEmpty()) {
            user = userRepository.findByEmail(email);
        }
        if (user.isEmpty()) {
            throw new RuntimeException("User not found");
        }
        if (!passwordEncoder.matches(password, user.get().getPassword())) {
            throw new RuntimeException("Invalid password");
        }
        return true;
    }
}
