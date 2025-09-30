package com.example.demo.controller;

import com.example.demo.entity.Student;
import com.example.demo.entity.User;
import com.example.demo.model.RegisterRequest;
import com.example.demo.model.LoginRequest;
import com.example.demo.model.LoginResponse;
import com.example.demo.repository.StudentRepository;
import com.example.demo.repository.UserRepository;
import com.example.demo.security.JwtUtil;

import jakarta.servlet.http.HttpServletRequest;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.crypto.password.PasswordEncoder;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = "http://localhost:4200")
public class AuthController {

    @GetMapping("/validate-token")
    public ResponseEntity<?> validateToken(HttpServletRequest request) {
        String authHeader = request.getHeader("Authorization");
        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            String token = authHeader.substring(7);
            try {
                String email = jwtUtil.extractUsername(token);
                User user = userRepository.findByEmail(email);
                if (user != null && jwtUtil.validateToken(token)) {
                    return ResponseEntity.ok(Map.of("role", user.getRole()));
                }
            } catch (Exception e) {
                return ResponseEntity.status(401).body("Invalid token");
            }
        }
        return ResponseEntity.status(401).body("No token provided");
    }

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtUtil jwtUtil;

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody RegisterRequest registerRequest) {
        if (!registerRequest.getPassword().equals(registerRequest.getConfirmPassword())) {
            return ResponseEntity.badRequest().body("Passwords do not match");
        }

        if (userRepository.findByEmail(registerRequest.getEmail()) != null) {
            return ResponseEntity.badRequest().body("Email already exists");
        }

        String role = registerRequest.getRole().toUpperCase();
        if (!role.equals("ADMIN") && !role.equals("STUDENT")) {
            return ResponseEntity.badRequest().body("Invalid role. Must be either ADMIN or STUDENT");
        }

        User user = new User();
        user.setName(registerRequest.getName());
        user.setEmail(registerRequest.getEmail());
        user.setPassword(passwordEncoder.encode(registerRequest.getPassword()));
        user.setPhone(registerRequest.getPhone());
        user.setRole(role);

        user = userRepository.save(user);

        if (role.equals("STUDENT")) {
            Student student = new Student();
            student.setId(user.getId());
            student.setName(user.getName());
            student.setEmail(user.getEmail());
            studentRepository.save(student);
        }

        String token = jwtUtil.generateToken(user.getEmail());
        return ResponseEntity.ok(new LoginResponse(token, user.getRole(),user.getName()));
    }

    @PostMapping("/login")
public ResponseEntity<?> login(@RequestBody LoginRequest loginRequest) {
    User user = userRepository.findByEmail(loginRequest.getEmail());

    if (user != null && passwordEncoder.matches(loginRequest.getPassword(), user.getPassword())) {
        String token = jwtUtil.generateToken(user.getEmail());
        return ResponseEntity.ok(new LoginResponse(token, user.getRole(), user.getName()));
    }

    return ResponseEntity.badRequest().body("Invalid email or password");
}
}
