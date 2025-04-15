package com.example.demo.controller;

import java.util.HashMap;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import com.example.demo.entity.Student;
import com.example.demo.entity.User;
import com.example.demo.exception.ResourceNotFoundException;
import com.example.demo.repository.StudentRepository;
import com.example.demo.repository.UserRepository;

@RestController
@RequestMapping("/api/student")  // Changed from /student to /api/student
@CrossOrigin(origins = "http://localhost:4200")
public class StudentController {

    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private UserRepository userRepository;

    @GetMapping("/batch-info")
    public ResponseEntity<?> getBatchInfo(Authentication authentication) {
        String email = authentication.getName();
        
        // Find user first
        User user = userRepository.findByEmail(email);
        if (user == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body("User not found");
        }

        // Create student record if it doesn't exist
        Student student = studentRepository.findByEmail(email);
        if (student == null) {
            student = new Student();
            student.setId(user.getId());
            student.setName(user.getName());
            student.setEmail(user.getEmail());
            studentRepository.save(student);
            return ResponseEntity.ok("Student record created. No batch allocated yet.");
        }

        if (student.getBatch() == null) {
            return ResponseEntity.ok("No batch allocated yet");
        }

        Map<String, Object> batchInfo = new HashMap<>();
        batchInfo.put("batchName", student.getBatch().getBatchName());
        batchInfo.put("startDate", student.getBatch().getStartDate());
        batchInfo.put("endDate", student.getBatch().getEndDate());
        batchInfo.put("facultyName", student.getBatch().getFaculty().getFacultyName());
        batchInfo.put("venueName", student.getBatch().getVenue().getVenueName());
        batchInfo.put("venueLocation", student.getBatch().getVenue().getLocation());

        return ResponseEntity.ok(batchInfo);
    }
}