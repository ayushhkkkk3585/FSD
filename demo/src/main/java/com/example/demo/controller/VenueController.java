package com.example.demo.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.demo.entity.Venue;
import com.example.demo.repository.VenueRepository;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/venues")
@Tag(name = "Venue Management", description = "Operations related to venue management")
public class VenueController {

    @Autowired 
    private VenueRepository venueRepo;

    // Get all venues
    @GetMapping
    @Operation(summary = "Get all venues", description = "Retrieve a list of all venues")
    public List<Venue> getAllVenues() {
        return venueRepo.findAll();
    }

    // Get venue by ID
    @GetMapping("/{id}")
    @Operation(summary = "Get a venue by ID", description = "Retrieve a venue by its ID")
    public ResponseEntity<Venue> getVenueById(@PathVariable Long id) {
        Optional<Venue> venue = venueRepo.findById(id);
        return venue.map(ResponseEntity::ok)
                    .orElseGet(() -> ResponseEntity.notFound().build());
    }

    // Create new venue (POST)
    @PostMapping
    @Operation(summary = "Create a new venue", description = "Add a new venue to the database")
    public ResponseEntity<Venue> createVenue(@RequestBody Venue venue) {
        Venue newVenue = venueRepo.save(venue);
        return ResponseEntity.ok(newVenue);
    }

    // Update entire venue record (PUT)
    @PutMapping("/{id}")
    @Operation(summary = "Update venue record", description = "Update the details of an existing venue")
    public ResponseEntity<Venue> updateVenue(@PathVariable Long id, @RequestBody Venue updatedVenue) {
        return venueRepo.findById(id).map(existingVenue -> {
            if (updatedVenue.getVenueName() != null) {
                existingVenue.setVenueName(updatedVenue.getVenueName());
            }
            if (updatedVenue.getLocation() != null) {
                existingVenue.setLocation(updatedVenue.getLocation());
            }
            if (updatedVenue.getCapacity() > 0) { // Assuming capacity should be positive
                existingVenue.setCapacity(updatedVenue.getCapacity());
            }
            Venue savedVenue = venueRepo.save(existingVenue);
            return ResponseEntity.ok(savedVenue);
        }).orElseGet(() -> ResponseEntity.notFound().build());
    }

    // Partially update venue record (PATCH)
    @PatchMapping("/{id}")
    @Operation(summary = "Partially update venue record", description = "Update specific fields of a venue record")
    public ResponseEntity<Venue> partiallyUpdateVenue(@PathVariable Long id, @RequestBody Venue partialVenue) {
        return venueRepo.findById(id).map(existingVenue -> {
            if (partialVenue.getVenueName() != null) {
                existingVenue.setVenueName(partialVenue.getVenueName());
            }
            if (partialVenue.getLocation() != null) {
                existingVenue.setLocation(partialVenue.getLocation());
            }
            if (partialVenue.getCapacity() > 0) { // Assuming capacity should be positive
                existingVenue.setCapacity(partialVenue.getCapacity());
            }
            Venue savedVenue = venueRepo.save(existingVenue);
            return ResponseEntity.ok(savedVenue);
        }).orElseGet(() -> ResponseEntity.notFound().build());
    }

    // Delete venue by ID
    @DeleteMapping("/{id}")
    @Operation(summary = "Delete venue by ID", description = "Delete a venue by its ID")
    public ResponseEntity<Void> deleteVenue(@PathVariable Long id) {
        if (venueRepo.existsById(id)) {
            venueRepo.deleteById(id);
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
