package com.example.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.demo.entity.Allocation;
import com.example.demo.repository.AllocationRepository;
import com.example.demo.exception.ResourceNotFoundException;
import com.example.demo.exception.InvalidDataException;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/allocations")
@Tag(name = "Allocation Management", description = "Operations related to allocation management")
public class AllocationController {

    @Autowired
    private AllocationRepository allocationRepo;

    // Get all allocations
    @GetMapping
    @Operation(summary = "Get all allocations", description = "Retrieve a list of all allocations")
    public List<Allocation> getAllAllocations() {
        return allocationRepo.findAll();
    }

    // Get allocation by ID
    @GetMapping("/{id}")
    @Operation(summary = "Get an allocation by ID", description = "Retrieve an allocation by its ID")
    public ResponseEntity<Allocation> getAllocationById(@PathVariable Integer id) {
        Allocation allocation = allocationRepo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Allocation not found with ID: " + id));
        return ResponseEntity.ok(allocation);
    }

    // Create a new allocation
    @PostMapping
    @Operation(summary = "Create a new allocation", description = "Add a new allocation to the database")
    public ResponseEntity<Allocation> createAllocation(@RequestBody Allocation allocation) {
        if (allocation.getFacultyId() == null) {
            throw new InvalidDataException("Faculty ID cannot be null");
        }
        Allocation newAllocation = allocationRepo.save(allocation);
        return ResponseEntity.ok(newAllocation);
    }

    // Other methods (PUT, PATCH, DELETE) as needed
}
