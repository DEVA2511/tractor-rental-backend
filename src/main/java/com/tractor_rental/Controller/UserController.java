package com.tractor_rental.Controller;

import com.tractor_rental.Service.authuser.JwtUtil;
import com.tractor_rental.Service.UserService;
import com.tractor_rental.modal.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/user")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private JwtUtil jwtUtil;

    @GetMapping
    public ResponseEntity<List<User>> getAllUsers() {
        List<User> users = userService.getAllUsers();
        return ResponseEntity.ok(users);
    }

    // New endpoint to refresh the token
    @PostMapping("/refresh-token")
    public ResponseEntity<String> refreshToken(@RequestHeader("Authorization") String oldToken) {
        String token = oldToken.replace("Bearer ", "");  // Extract the JWT token from the Authorization header
        if (jwtUtil.isTokenExpired(token)) {
            String email = jwtUtil.extractEmail(token);  // Extract the email from the expired token
            String role = jwtUtil.extractRole(token);    // Extract the role
            String newToken = jwtUtil.generateToken(email, role);  // Generate a new token
            return ResponseEntity.ok(newToken);
        } else {
            return ResponseEntity.status(400).body("Token is still valid, no need to refresh.");
        }
    }
}
