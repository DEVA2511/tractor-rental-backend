//package com.tractor_rental.Config;
//
//import com.tractor_rental.Service.authuser.JwtService;
//import com.tractor_rental.modal.CustomOAuth2User;
//import com.tractor_rental.modal.User;
//import com.tractor_rental.repositories.UserRepository;
//import jakarta.servlet.ServletException;
//import jakarta.servlet.http.HttpServletRequest;
//import jakarta.servlet.http.HttpServletResponse;
//import org.springframework.security.core.Authentication;
//import org.springframework.security.crypto.password.PasswordEncoder;
//import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
//import org.springframework.stereotype.Component;
//
//import java.io.IOException;
//import java.util.UUID;
//
//@Component
//public class OAuth2LoginSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {
//    private final UserRepository userRepository;
//    private final JwtService jwtService;
//    private final PasswordEncoder passwordEncoder;
//
//    public OAuth2LoginSuccessHandler(UserRepository userRepository, JwtService jwtService, PasswordEncoder passwordEncoder) {
//        this.userRepository = userRepository;
//        this.jwtService = jwtService;
//        this.passwordEncoder = passwordEncoder;
//    }
//
//    @Override
//    public void onAuthenticationSuccess(HttpServletRequest request,
//                                        HttpServletResponse response,
//                                        Authentication authentication) throws IOException {
//        CustomOAuth2User oauthUser = (CustomOAuth2User) authentication.getPrincipal();
//        String email = oauthUser.getEmail();
//
//        // Find or create user
//        User user = userRepository.findByEmail(email).orElseGet(() -> {
//            User newUser = new User();
//            newUser.setEmail(email);
//            newUser.setFirstName(oauthUser.getFirstName());
//            newUser.setLastName(oauthUser.getLastName());
//            newUser.setRole("USER");
////            newUser.setProvider(oauthUser.getRegistrationId());
//            newUser.setPassword(passwordEncoder.encode(UUID.randomUUID().toString()));
//            return userRepository.save(newUser);
//        });
//
//        // Generate actual JWT token
//        String token = jwtService.generateToken(user);
//
//        // Redirect to frontend with token
//        String redirectUrl = "http://localhost:4200/oauth-callback?token=" + token + "&email=" + email;
//        getRedirectStrategy().sendRedirect(request, response, redirectUrl);
//    }
//
//}

package com.tractor_rental.Config;


import com.tractor_rental.Service.authuser.JwtUtil;
import com.tractor_rental.modal.CustomOAuth2User;
import com.tractor_rental.modal.User;
import com.tractor_rental.repositories.UserRepository;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.UUID;
@Component
@RequiredArgsConstructor
public class OAuth2LoginSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {
    private final UserRepository userRepository;
    private final JwtUtil jwtUtil; // Changed from JwtService to JwtUtil
    private final PasswordEncoder passwordEncoder;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request,
                                        HttpServletResponse response,
                                        Authentication authentication) throws IOException {
        CustomOAuth2User oauthUser = (CustomOAuth2User) authentication.getPrincipal();
        String email = oauthUser.getEmail();

        // Find or create user
        User user = userRepository.findByEmail(email).orElseGet(() -> {
            User newUser = new User();
            newUser.setEmail(email);
            newUser.setFirstName(oauthUser.getFirstName());
            newUser.setLastName(oauthUser.getLastName());
            newUser.setRole("USER");
            newUser.setPassword(passwordEncoder.encode(UUID.randomUUID().toString()));
            return userRepository.save(newUser);
        });

        try {
            // Generate JWT token
            String token = jwtUtil.generateToken(user.getEmail(), user.getRole());

            // Construct redirect URL with token as query parameter
            String redirectUrl = "http://localhost:4200/oauth-callback" +
                    "?token=" + token +
                    "&email=" + URLEncoder.encode(user.getEmail(), StandardCharsets.UTF_8.toString()) +
                    "&name=" + URLEncoder.encode(user.getFirstName(), StandardCharsets.UTF_8.toString());

            // Redirect to Angular frontend
            response.sendRedirect(redirectUrl);
            System.out.println("Redirecting to: " + redirectUrl);
        } catch (Exception e) {
            logger.error("OAuth token generation failed", e);
            response.sendRedirect("http://localhost:4200/login?error=oauth_failed");
        }
    }
}

