package com.tractor_rental.Service;

import com.tractor_rental.modal.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class JwtService {
    private final JwtUtil jwtUtil;

    public String generateToken(User user) {
        return jwtUtil.generateToken(user.getEmail(), user.getRole());
    }
}