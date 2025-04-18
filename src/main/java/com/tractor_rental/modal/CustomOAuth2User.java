package com.tractor_rental.modal;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.core.user.OAuth2User;

import java.util.Collection;
import java.util.Map;

public class CustomOAuth2User implements OAuth2User {
    private OAuth2User oauth2User;
    private String registrationId;
    private User user = new User();

    public CustomOAuth2User(OAuth2User oauth2User, User registrationId) {
        this.oauth2User = oauth2User;
        this.registrationId = String.valueOf(registrationId);
        this.user = user;
    }

    @Override
    public Map<String, Object> getAttributes() {
        return oauth2User.getAttributes();
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return oauth2User.getAuthorities();
    }

    @Override
    public String getName() {
        return oauth2User.getAttribute("name");
    }

    public String getEmail() {
        return oauth2User.getAttribute("email");
    }

    public String getRegistrationId() {
        return registrationId;
    }

    public String getFullName() {
        return oauth2User.getAttribute("name");
    }

    public String getFirstName() {
        String name = getName();
        return name != null ? name.split(" ")[0] : "";
    }

    public String getLastName() {
        String name = getName();
        return name != null && name.split(" ").length > 1 ? name.split(" ")[1] : "";
    }
    public User getUser() {
        return user;
    }


}