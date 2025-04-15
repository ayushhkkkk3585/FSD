package com.example.demo.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.demo.entity.Batch;
import com.example.demo.repository.BatchRepository;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/batches")
@Tag(name = "Batch Management", description = "Operations related to batch management")
public class BatchController {

    @Autowired 
    private BatchRepository batchRepo;

    // Get all batches
    @GetMapping
    @Operation(summary = "Get all batches", description = "Retrieve a list of all batches")
    public List<Batch> getAllBatches() {
        return batchRepo.findAll();
    }

    // Get batch by ID
    @GetMapping("/{id}")
    @Operation(summary = "Get a batch by ID", description = "Retrieve a batch by its ID")
    public ResponseEntity<Batch> getBatchById(@PathVariable Integer id) {
        Optional<Batch> batch = batchRepo.findById(id);
        return batch.map(ResponseEntity::ok)
                    .orElseGet(() -> ResponseEntity.notFound().build());
    }

    // Create new batch (POST)
    @PostMapping
    @Operation(summary = "Create a new batch", description = "Add a new batch to the database")
    public ResponseEntity<Batch> createBatch(@RequestBody Batch batch) {
        Batch newBatch = batchRepo.save(batch);
        return ResponseEntity.ok(newBatch);
    }

    // Update entire batch record (PUT)
    @PutMapping("/{id}")
    @Operation(summary = "Update batch record", description = "Update the details of an existing batch")
    public ResponseEntity<Batch> updateBatch(@PathVariable Integer id, @RequestBody Batch updatedBatch) {
        return batchRepo.findById(id).map(existingBatch -> {
            if (updatedBatch.getBatchName() != null) {
                existingBatch.setBatchName(updatedBatch.getBatchName());
            }
            if (updatedBatch.getStartDate() != null) {
                existingBatch.setStartDate(updatedBatch.getStartDate());
            }
            if (updatedBatch.getEndDate() != null) {
                existingBatch.setEndDate(updatedBatch.getEndDate());
            }
            Batch savedBatch = batchRepo.save(existingBatch);
            return ResponseEntity.ok(savedBatch);
        }).orElseGet(() -> ResponseEntity.notFound().build());
    }

    // Partially update batch record (PATCH)
    @PatchMapping("/{id}")
    @Operation(summary = "Partially update batch record", description = "Update specific fields of a batch record")
    public ResponseEntity<Batch> partiallyUpdateBatch(@PathVariable Integer id, @RequestBody Batch partialBatch) {
        return batchRepo.findById(id).map(existingBatch -> {
            if (partialBatch.getBatchName() != null) {
                existingBatch.setBatchName(partialBatch.getBatchName());
            }
            if (partialBatch.getStartDate() != null) {
                existingBatch.setStartDate(partialBatch.getStartDate());
            }
            if (partialBatch.getEndDate() != null) {
                existingBatch.setEndDate(partialBatch.getEndDate());
            }
            Batch savedBatch = batchRepo.save(existingBatch);
            return ResponseEntity.ok(savedBatch);
        }).orElseGet(() -> ResponseEntity.notFound().build());
    }

    // Delete batch by ID
    @DeleteMapping("/{id}")
    @Operation(summary = "Delete batch by ID", description = "Delete a batch by its ID")
    public ResponseEntity<Void> deleteBatch(@PathVariable Integer id) {
        if (batchRepo.existsById(id)) {
            batchRepo.deleteById(id);
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
