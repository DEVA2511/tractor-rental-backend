//package com.tractor_rental.Controller;
//
//import com.tractor_rental.DTO.AuthenticationRequest;
//import com.tractor_rental.DTO.AuthenticationResponse;
//import com.tractor_rental.DTO.RegisterRequest;
//import com.tractor_rental.Service.authuser.AuthService;
//import jakarta.servlet.http.HttpServletRequest;
//import lombok.RequiredArgsConstructor;
//import org.springframework.http.ResponseEntity;
//import org.springframework.security.core.Authentication;
//import org.springframework.security.core.context.SecurityContextHolder;
//import org.springframework.web.bind.annotation.*;
//
//@RestController
//@RequestMapping("/api/auth")
//@RequiredArgsConstructor
//public class AuthenticationController {
//    private final AuthService authService;
//
//
//    @PostMapping("/login")
//    public ResponseEntity<AuthenticationResponse> login(@RequestBody AuthenticationRequest request) {
//        return ResponseEntity.ok(authService.authenticate(request));
//    }
//
//    @PostMapping("/register")
//    public ResponseEntity<AuthenticationResponse> register(@RequestBody RegisterRequest request) {
//        return ResponseEntity.ok(authService.register(request));
//    }
//
//    @GetMapping("/oauth2/google")
//    public ResponseEntity<String> googleLogin() {
//        return ResponseEntity.ok("Redirect to Google OAuth2");
//    }
//    @GetMapping("/api/auth/oauth2/success")
//    public ResponseEntity<AuthenticationResponse> oauth2Success(HttpServletRequest request) {
//        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//
//        return ResponseEntity.ok(AuthenticationResponse.builder().build());
//    }
//
//    @GetMapping("/oauth2/facebook")
//    public ResponseEntity<String> facebookLogin() {
//        return ResponseEntity.ok("Redirect to Facebook OAuth2");
//    }
//}

package com.tractor_rental.Controller;

import com.tractor_rental.DTO.AuthenticationRequest;
import com.tractor_rental.DTO.AuthenticationResponse;
import com.tractor_rental.DTO.RegisterRequest;
import com.tractor_rental.Service.authuser.AuthService;
import com.tractor_rental.Service.authuser.JwtUtil;
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
    private JwtUtil jwtUtil;


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
            User user = (User) auth.getPrincipal();
            String token = jwtUtil.generateToken(user.getEmail(), user.getRole());
            return ResponseEntity.ok(new AuthenticationResponse(token, user.getEmail(), user.getRole()));
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Unauthorized");
    }


}