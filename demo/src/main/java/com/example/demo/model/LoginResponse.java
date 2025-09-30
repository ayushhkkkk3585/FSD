package com.example.demo.model;

public class LoginResponse {
    private String token;
    private String role;
    private String name;

    public LoginResponse(String token, String role, String name) {
        this.token = token;
        this.role = role;
        this.name = name;
    }

    public String getToken() {
        return token;
    }

    public String getRole() {
        return role;
    }

    public String getName() {
        return name;
    }

    // Setters
    public void setToken(String token) {
        this.token = token;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public void setName(String name) {
        this.name = name;
    }
}