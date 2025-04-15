package com.example.demo.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.demo.entity.Student;
import com.example.demo.repository.StudentRepository;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/students")
@Tag(name = "Student Management", description = "Operations related to student management")
public class StudentController {
    
    @Autowired 
    private StudentRepository studrepo;

    // Get all students
    @GetMapping
    @Operation(summary = "Get all students", description = "Retrieve a list of all students")
    public List<Student> getAllStudents() {
        return studrepo.findAll();
    }

    // Get student by ID
    @GetMapping("/{id}")
    @Operation(summary = "Get a student by ID", description = "Retrieve a student by their ID")
    public ResponseEntity<Student> getStudentById(@PathVariable Long id) {
        Optional<Student> student = studrepo.findById(id);
        return student.map(ResponseEntity::ok)
                      .orElseGet(() -> ResponseEntity.notFound().build());
    }

    // Create new student (POST)
    @PostMapping
    @Operation(summary = "Create a new student", description = "Add a new student to the database")
    public ResponseEntity<Student> createStudent(@RequestBody Student student) {
        Student newStudent = studrepo.save(student);
        return ResponseEntity.ok(newStudent);
    }

    // Update entire student record (PUT)
    @PutMapping("/{id}")
    @Operation(summary = "Update student record", description = "Update the details of an existing student")
    public ResponseEntity<Student> updateStudent(@PathVariable Long id, @RequestBody Student updatedStudent) {
        return studrepo.findById(id).map(existingStudent -> {
            if (updatedStudent.getName() != null) {
                existingStudent.setName(updatedStudent.getName());
            }
            if (updatedStudent.getDepartment() != null) {
                existingStudent.setDepartment(updatedStudent.getDepartment());
            }
            Student savedStudent = studrepo.save(existingStudent);
            return ResponseEntity.ok(savedStudent);
        }).orElseGet(() -> ResponseEntity.notFound().build());
    }

    // Partially update student record (PATCH)
    @PatchMapping("/{id}")
    @Operation(summary = "Partially update student record", description = "Update specific fields of a student record")
    public ResponseEntity<Student> partiallyUpdateStudent(@PathVariable Long id, @RequestBody Student partialStudent) {
        return studrepo.findById(id).map(existingStudent -> {
            if (partialStudent.getName() != null) {
                existingStudent.setName(partialStudent.getName());
            }
            if (partialStudent.getDepartment() != null) {
                existingStudent.setDepartment(partialStudent.getDepartment());
            }
            Student savedStudent = studrepo.save(existingStudent);
            return ResponseEntity.ok(savedStudent);
        }).orElseGet(() -> ResponseEntity.notFound().build());
    }

    // Delete student by ID
    @DeleteMapping("/{id}")
    @Operation(summary = "Delete student by ID", description = "Delete a student by their ID")
    public ResponseEntity<Void> deleteStudent(@PathVariable Long id) {
        if (studrepo.existsById(id)) {
            studrepo.deleteById(id);
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
    
}
