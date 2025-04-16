package com.example.demo.controller;

import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import com.example.demo.dto.BatchDTO;
import com.example.demo.entity.Batch;
import com.example.demo.entity.Faculty;
import com.example.demo.entity.Venue;
import com.example.demo.exception.ResourceNotFoundException;
import com.example.demo.repository.BatchRepository;
import com.example.demo.repository.FacultyRepository;
import com.example.demo.repository.VenueRepository;

@RestController
@RequestMapping("/api/batches")
@CrossOrigin(origins = "http://localhost:4200")
public class BatchController {

    @Autowired
    private BatchRepository batchRepo;

    @Autowired
    private FacultyRepository facultyRepo;

    @Autowired
    private VenueRepository venueRepo;

    @GetMapping
    public List<BatchDTO> getAllBatches() {
        return batchRepo.findAll().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<BatchDTO> createBatch(@RequestBody BatchDTO batchDTO) {
        // Create Faculty first
        Faculty faculty = new Faculty();
        faculty.setFacultyName(batchDTO.getFacultyName());
        faculty.setEmail(batchDTO.getFacultyEmail());
        faculty.setDepartment(batchDTO.getDepartment());
        Faculty savedFaculty = facultyRepo.save(faculty);

        // Create Venue
        Venue venue = new Venue();
        venue.setVenueName(batchDTO.getVenueName());
        venue.setLocation(batchDTO.getVenueLocation());
        venue.setCapacity(batchDTO.getCapacity());
        Venue savedVenue = venueRepo.save(venue);

        // Create Batch with saved Faculty and Venue
        Batch batch = new Batch();
        batch.setBatchName(batchDTO.getBatchName());
        batch.setStartDate(batchDTO.getStartDate());
        batch.setEndDate(batchDTO.getEndDate());
        batch.setFaculty(savedFaculty);
        batch.setVenue(savedVenue);

        Batch savedBatch = batchRepo.save(batch);
        return ResponseEntity.ok(convertToDTO(savedBatch));
    }

    @GetMapping("/{id}")
    public ResponseEntity<BatchDTO> getBatchById(@PathVariable Integer id) {
        Batch batch = batchRepo.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Batch not found"));
        return ResponseEntity.ok(convertToDTO(batch));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<BatchDTO> updateBatch(@PathVariable Integer id, @RequestBody BatchDTO batchDTO) {
        Batch batch = batchRepo.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Batch not found"));

        batch.setBatchName(batchDTO.getBatchName());
        batch.setStartDate(batchDTO.getStartDate());
        batch.setEndDate(batchDTO.getEndDate());

        if (batch.getFaculty() != null) {
            Faculty faculty = batch.getFaculty();
            faculty.setFacultyName(batchDTO.getFacultyName());
            faculty.setEmail(batchDTO.getFacultyEmail());
            faculty.setDepartment(batchDTO.getDepartment());
            facultyRepo.save(faculty);
        }

        if (batch.getVenue() != null) {
            Venue venue = batch.getVenue();
            venue.setVenueName(batchDTO.getVenueName());
            venue.setLocation(batchDTO.getVenueLocation());
            venue.setCapacity(batchDTO.getCapacity());
            venueRepo.save(venue);
        }

        Batch updatedBatch = batchRepo.save(batch);
        return ResponseEntity.ok(convertToDTO(updatedBatch));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> deleteBatch(@PathVariable Integer id) {
        Batch batch = batchRepo.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Batch not found"));
        batchRepo.delete(batch);
        return ResponseEntity.ok().build();
    }

    private BatchDTO convertToDTO(Batch batch) {
        BatchDTO dto = new BatchDTO();
        dto.setBatchId(batch.getBatchId());
        dto.setBatchName(batch.getBatchName());
        dto.setStartDate(batch.getStartDate());
        dto.setEndDate(batch.getEndDate());
        
        if (batch.getFaculty() != null) {
            dto.setFacultyName(batch.getFaculty().getFacultyName());
            dto.setFacultyEmail(batch.getFaculty().getEmail());
            dto.setDepartment(batch.getFaculty().getDepartment());
        }
        
        if (batch.getVenue() != null) {
            dto.setVenueName(batch.getVenue().getVenueName());
            dto.setVenueLocation(batch.getVenue().getLocation());
            dto.setCapacity(batch.getVenue().getCapacity());
        }
        
        return dto;
    }
}