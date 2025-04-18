package com.example.demo.model;

public class RegisterRequest {
    private String name;
    private String email;
    private String phone;
    private String password;
    private String confirmPassword;
    private String role;  // Added role field
    
    // Getters and Setters
    public String getName() {
        return name;
    }
    
    public void setName(String name) {
        this.name = name;
    }
    
    public String getEmail() {
        return email;
    }
    
    public void setEmail(String email) {
        this.email = email;
    }
    
    public String getPhone() {
        return phone;
    }
    
    public void setPhone(String phone) {
        this.phone = phone;
    }
    
    public String getPassword() {
        return password;
    }
    
    public void setPassword(String password) {
        this.password = password;
    }
    
    public String getConfirmPassword() {
        return confirmPassword;
    }
    
    public void setConfirmPassword(String confirmPassword) {
        this.confirmPassword = confirmPassword;
    }
     // Add role getter and setter
     public String getRole() {
        return role;
    }
    
    public void setRole(String role) {
        this.role = role;
    }
}