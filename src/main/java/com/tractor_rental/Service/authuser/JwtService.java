package com.tractor_rental.Service.authuser;

import com.tractor_rental.modal.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class JwtService {
    private final JwtUtil jwtUtil;

    public String generateToken(User user) {
        String token = jwtUtil.generateToken(user.getEmail(), user.getRole());
        return token;
    }
}