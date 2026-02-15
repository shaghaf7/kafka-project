package com.backend.microservices.controller;


import com.backend.microservices.model.User;
import com.backend.microservices.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;


    @PostMapping("/signup")
    public ResponseEntity<?> signup(@RequestBody User user){
        User saveduser=userService.register(user);
        return ResponseEntity.ok(saveduser);
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody User user) {
        Optional<User> loggedInUser =
                userService.login(user.getUsername(), user.getPassword());

        if (loggedInUser.isPresent()) {
            return ResponseEntity.ok("Login successful");
        }

        return ResponseEntity.badRequest().body("Invalid username or password");
    }
}
