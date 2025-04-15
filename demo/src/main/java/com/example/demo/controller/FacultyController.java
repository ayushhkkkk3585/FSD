package com.example.demo.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.demo.entity.Faculty;
import com.example.demo.repository.FacultyRepository;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/faculties")
@Tag(name = "Faculty Management", description = "Operations related to faculty management")
public class FacultyController {

    @Autowired 
    private FacultyRepository facultyRepo;

    // Get all faculties
    @GetMapping
    @Operation(summary = "Get all faculties", description = "Retrieve a list of all faculties")
    public List<Faculty> getAllFaculties() {
        return facultyRepo.findAll();
    }

    // Get faculty by ID
    @GetMapping("/{id}")
    @Operation(summary = "Get a faculty by ID", description = "Retrieve a faculty by its ID")
    public ResponseEntity<Faculty> getFacultyById(@PathVariable Integer id) {
        Optional<Faculty> faculty = facultyRepo.findById(id);
        return faculty.map(ResponseEntity::ok)
                      .orElseGet(() -> ResponseEntity.notFound().build());
    }

    // Create new faculty (POST)
    @PostMapping
    @Operation(summary = "Create a new faculty", description = "Add a new faculty to the database")
    public ResponseEntity<Faculty> createFaculty(@RequestBody Faculty faculty) {
        Faculty newFaculty = facultyRepo.save(faculty);
        return ResponseEntity.ok(newFaculty);
    }

    // Update entire faculty record (PUT)
    @PutMapping("/{id}")
    @Operation(summary = "Update faculty record", description = "Update the details of an existing faculty")
    public ResponseEntity<Faculty> updateFaculty(@PathVariable Integer id, @RequestBody Faculty updatedFaculty) {
        return facultyRepo.findById(id).map(existingFaculty -> {
            if (updatedFaculty.getFacultyName() != null) {
                existingFaculty.setFacultyName(updatedFaculty.getFacultyName());
            }
            if (updatedFaculty.getDepartment() != null) {
                existingFaculty.setDepartment(updatedFaculty.getDepartment());
            }
            if (updatedFaculty.getEmail() != null) {
                existingFaculty.setEmail(updatedFaculty.getEmail());
            }
            Faculty savedFaculty = facultyRepo.save(existingFaculty);
            return ResponseEntity.ok(savedFaculty);
        }).orElseGet(() -> ResponseEntity.notFound().build());
    }

    // Partially update faculty record (PATCH)
    @PatchMapping("/{id}")
    @Operation(summary = "Partially update faculty record", description = "Update specific fields of a faculty record")
    public ResponseEntity<Faculty> partiallyUpdateFaculty(@PathVariable Integer id, @RequestBody Faculty partialFaculty) {
        return facultyRepo.findById(id).map(existingFaculty -> {
            if (partialFaculty.getFacultyName() != null) {
                existingFaculty.setFacultyName(partialFaculty.getFacultyName());
            }
            if (partialFaculty.getDepartment() != null) {
                existingFaculty.setDepartment(partialFaculty.getDepartment());
            }
            if (partialFaculty.getEmail() != null) {
                existingFaculty.setEmail(partialFaculty.getEmail());
            }
            Faculty savedFaculty = facultyRepo.save(existingFaculty);
            return ResponseEntity.ok(savedFaculty);
        }).orElseGet(() -> ResponseEntity.notFound().build());
    }

    // Delete faculty by ID
    @DeleteMapping("/{id}")
    @Operation(summary = "Delete faculty by ID", description = "Delete a faculty by its ID")
    public ResponseEntity<Void> deleteFaculty(@PathVariable Integer id) {
        if (facultyRepo.existsById(id)) {
            facultyRepo.deleteById(id);
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
