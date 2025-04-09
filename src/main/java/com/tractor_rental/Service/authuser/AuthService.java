package com.tractor_rental.Service.authuser;

import com.tractor_rental.DTO.AuthenticationRequest;
import com.tractor_rental.DTO.AuthenticationResponse;
import com.tractor_rental.DTO.RegisterRequest;
import com.tractor_rental.Service.UserService;
import com.tractor_rental.modal.User;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Service;

import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final AuthenticationManager authenticationManager;
    private final UserService userService;
    private final JwtService jwtService;

    public AuthenticationResponse authenticate(AuthenticationRequest request) {
        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            request.getEmail(),
                            request.getPassword()
                    )
            );

            String role = authentication.getAuthorities().stream()
                    .map(GrantedAuthority::getAuthority)
                    .collect(Collectors.toList()).get(0);

            String token = jwtService.generateToken(
                    userService.getUserByEmail(request.getEmail())
            );

            return AuthenticationResponse.builder()
                    .token(token)
                    .email(request.getEmail())
                    .role(role)
                    .build();

        } catch (BadCredentialsException e) {
            throw new RuntimeException("Incorrect email or password", e);
        }
    }

    public AuthenticationResponse register(RegisterRequest request) {
        User user = userService.register(request);
        String token = jwtService.generateToken(user);

        return AuthenticationResponse.builder()
                .token(token)
                .email(user.getEmail())
                .role(user.getRole())
                .build();
    }
}