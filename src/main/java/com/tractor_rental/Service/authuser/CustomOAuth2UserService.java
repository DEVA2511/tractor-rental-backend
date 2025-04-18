package com.tractor_rental.Service.authuser;

import com.tractor_rental.modal.CustomOAuth2User;
import com.tractor_rental.modal.User;
import com.tractor_rental.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import java.util.UUID;
@Service
@RequiredArgsConstructor
public class CustomOAuth2UserService extends DefaultOAuth2UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        try {
            OAuth2User oAuth2User = super.loadUser(userRequest);
            System.out.println("OAuth2User loaded: " + oAuth2User.getAttributes());

            // Extract user info
            String email = oAuth2User.getAttribute("email");
            String name = oAuth2User.getAttribute("name");
            String firstName = name != null ? name.split(" ")[0] : "";
            String lastName = name != null && name.split(" ").length > 1 ? name.split(" ")[1] : "";

            if (email == null) {
                throw new OAuth2AuthenticationException("Email not found in OAuth2 response");
            }

            // Find or create user
            User user = userRepository.findByEmail(email)
                    .orElseGet(() -> {
                        User newUser = User.builder()
                                .email(email)
                                .firstName(firstName)
                                .lastName(lastName)
                                .password(passwordEncoder.encode(UUID.randomUUID().toString()))
                                .role("ADMIN") // Default role
                                .build();
                        return userRepository.save(newUser);
                    });

            return new CustomOAuth2User(oAuth2User, user);
        } catch (Exception e) {
            throw new OAuth2AuthenticationException("OAuth2 processing failed");
        }
    }
}