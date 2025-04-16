package com.example.demo.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import com.example.demo.dto.BatchDTO;
import com.example.demo.entity.Batch;
import com.example.demo.entity.Student;
import com.example.demo.entity.User;
import com.example.demo.repository.BatchRepository;
import com.example.demo.repository.StudentRepository;
import com.example.demo.repository.UserRepository;
@RestController
@RequestMapping("/api/student")
@CrossOrigin(origins = "http://localhost:4200")
public class StudentController {

    @Autowired
    private BatchRepository batchRepository;

    @Autowired
    private StudentRepository studentRepository;

    @GetMapping("/available-batches")
    public ResponseEntity<List<BatchDTO>> getAvailableBatches() {
        List<Batch> batches = batchRepository.findAll();
        List<BatchDTO> batchDTOs = batches.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
        return ResponseEntity.ok(batchDTOs);
    }

    @GetMapping("/my-batch")
    public ResponseEntity<?> getMyBatch(Authentication authentication) {
        String email = authentication.getName();
        Student student = studentRepository.findByEmail(email);
        
        if (student == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Student not found");
        }

        if (student.getBatch() == null) {
            return ResponseEntity.ok("No batch allocated yet");
        }

        return ResponseEntity.ok(convertToDTO(student.getBatch()));
    }

    private BatchDTO convertToDTO(Batch batch) {
        BatchDTO dto = new BatchDTO();
        dto.setBatchId(batch.getBatchId());
        dto.setBatchName(batch.getBatchName());
        dto.setStartDate(batch.getStartDate());
        dto.setEndDate(batch.getEndDate());
        if (batch.getFaculty() != null) {
            dto.setFacultyName(batch.getFaculty().getFacultyName());
        }
        if (batch.getVenue() != null) {
            dto.setVenueName(batch.getVenue().getVenueName());
            dto.setVenueLocation(batch.getVenue().getLocation());
        }
        return dto;
    }
}