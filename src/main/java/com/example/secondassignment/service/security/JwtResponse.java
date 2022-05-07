package com.example.secondassignment.service.security;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@AllArgsConstructor
@Getter
@Setter
@NoArgsConstructor
public class JwtResponse {
    private String accessToken;
    private String type = "Bearer";
    private String email;
    private String role;
    private Boolean hasRestaurant;

    public JwtResponse(String accessToken, String email, String role, Boolean hasRestaurant) {
        this.accessToken = accessToken;
        this.email = email;
        this.role = role;
        this.hasRestaurant = hasRestaurant;
    }

    public String getTokenType() {
        return type;
    }

    public void setTokenType(String tokenType) {
        this.type = tokenType;
    }
}
