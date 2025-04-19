
package com.tractor_rental.Controller;

import com.tractor_rental.DTO.AuthenticationRequest;
import com.tractor_rental.DTO.AuthenticationResponse;
import com.tractor_rental.DTO.RegisterRequest;
import com.tractor_rental.Service.authuser.AuthService;
import com.tractor_rental.Service.authuser.JwtUtil;
import com.tractor_rental.modal.CustomOAuth2User;
import com.tractor_rental.modal.User;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthenticationController {
    private final AuthService authService;
    private final JwtUtil jwtUtil;


    @PostMapping("/login")
    public ResponseEntity<AuthenticationResponse> login(@RequestBody AuthenticationRequest request) {
        return ResponseEntity.ok(authService.authenticate(request));
    }

    @PostMapping("/register")
    public ResponseEntity<AuthenticationResponse> register(@RequestBody RegisterRequest request) {
        return ResponseEntity.ok(authService.register(request));
    }

    @GetMapping("/oauth2/success")
    public ResponseEntity<?> oauth2Success(HttpServletRequest request) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        if (auth != null && auth.isAuthenticated()) {
            CustomOAuth2User customUser = (CustomOAuth2User) auth.getPrincipal();
            User user = customUser.getUser(); // Get full user with role
            String token = jwtUtil.generateToken(user.getEmail(), user.getRole());
            return ResponseEntity.ok(new AuthenticationResponse(token, user.getEmail(), user.getRole()));
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Unauthorized");
    }


}