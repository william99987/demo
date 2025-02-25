//package com.example.demo.service;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Component;
//import org.springframework.stereotype.Service;
//import java.util.Optional;
//import com.example.demo.repository.UserRepository;
//import com.example.demo.entity.User;
//import org.springframework.security.crypto.password.PasswordEncoder;
//
//@Service
//public class UserService {
//
//    @Autowired
//    private UserRepository userRepository;
//
//    @Autowired
//    private PasswordEncoder passwordEncoder;
//
//    public User userRegister(String username, String password, String email) {
//
//        if (username == null || password == null || email == null) {
//            throw new RuntimeException("Invalid input");
//        }
//
//        if (username.length() < 3 || username.length() > 20) {
//            throw new RuntimeException("Username must be between 3 and 20 characters");
//        }
//
//        if (password.length() < 8 || password.length() > 20) {
//            throw new RuntimeException("Password must be between 8 and 20 characters");
//        }
//
//        if (!email.matches("^[a-zA-Z0-9+_.-]+@[a-zA-Z0-9.-]+$")) {
//            throw new RuntimeException("Invalid email");
//        }
//
//        if (!username.matches("^[a-zA-Z0-9]+$")) {
//            throw new RuntimeException("Username must contain only alphanumeric characters");
//        }
//
//        if (userRepository.findByUsername(username).isPresent()) {
//            throw new RuntimeException("Username already exists");
//        }
//
//        String encodedPassword = passwordEncoder.encode(password);
//        User user = new User(username, encodedPassword, email);
//        return userRepository.save(user);
//    }
//
//    public boolean userLogin(String username, String email, String password) {
//        Optional<User> user = userRepository.findByUsername(username);
//        if (user.isEmpty()) {
//            user = userRepository.findByEmail(email);
//        }
//        if (user.isEmpty()) {
//            throw new RuntimeException("User not found");
//        }
//        if (!passwordEncoder.matches(password, user.get().getPassword())) {
//            throw new RuntimeException("Invalid password");
//        }
//        return true;
//    }
//}


package com.example.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.Optional;
import com.example.demo.repository.UserRepository;
import com.example.demo.entity.User;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    // User registration without password encoding
    public User userRegister(String username, String password, String email) {

        System.out.println("Test");

        if (username == null || password == null || email == null) {
            throw new RuntimeException("Invalid input");
        }

        if (username.length() < 3 || username.length() > 20) {
            throw new RuntimeException("Username must be between 3 and 20 characters");
        }

        if (password.length() < 8 || password.length() > 20) {
            throw new RuntimeException("Password must be between 8 and 20 characters");
        }

//        if (!email.matches("^[a-zA-Z0-9+_.-]+@[a-zA-Z0-9.-]+$")) {
//            throw new RuntimeException("Invalid email");
//        }
//
//        if (!username.matches("^[a-zA-Z0-9]+$")) {
//            throw new RuntimeException("Username must contain only alphanumeric characters");
//        }

        System.out.println("Test2");

        if (userRepository.findByUsername(username).isPresent()) {
            throw new RuntimeException("Username already exists");
        }

        System.out.println("Test3");

        // Save the user with the plain text password
        User user = new User(username, password, email);
        return userRepository.save(user);
    }

    // User login without password checking
    public boolean userLogin(String username, String email, String password) {
        Optional<User> user = userRepository.findByUsername(username);
        if (user.isEmpty()) {
            user = userRepository.findByEmail(email);
        }
        if (user.isEmpty()) {
            throw new RuntimeException("User not found");
        }

        // Direct password comparison without hashing
        if (!password.equals(user.get().getPassword())) {
            throw new RuntimeException("Invalid password");
        }
        return true;
    }
}

