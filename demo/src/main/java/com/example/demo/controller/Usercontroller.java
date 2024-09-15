package com.example.demo.controller;

import com.example.demo.model.User;
import com.example.demo.service.UserService;
import com.example.demo.security.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@RestController
@RequestMapping("/api")
public class Usercontroller {

    private static final Logger logger = LoggerFactory.getLogger(Usercontroller.class);

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private UserService userService;

    @GetMapping("/hii")
    public ResponseEntity<String> demol(){
        logger.info("Received request for /hii endpoint");
        return ResponseEntity.ok("Demoooooooooooooooooooo");
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody User loginRequest) {
        logger.info("Received login request: {}", loginRequest);  

        if (loginRequest == null) {
            logger.info("Login request is null");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid login request");
        }
        
        logger.info("Processing login request -------------------");
        String username = loginRequest.getUsername();
        String password = loginRequest.getPassword();
        logger.info("Username: {}", username);

        User user = userService.findByUsername(username);

        if (user != null && password.equals(user.getPassword())) {
        	logger.info("inside user");
            String token = jwtUtil.generateToken(username);
            return ResponseEntity.ok(token);
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid credentials");
        }
    }
}
