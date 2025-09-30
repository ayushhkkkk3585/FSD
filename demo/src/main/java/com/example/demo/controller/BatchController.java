package com.example.demo.controller;

import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

    private static final Logger logger = LoggerFactory.getLogger(BatchController.class);

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
    @Transactional
    public ResponseEntity<BatchDTO> createBatch(@RequestBody BatchDTO batchDTO) {
        try {
            // Log the incoming batch data
            logger.info("Creating batch with data: {}", batchDTO);

            // Create Faculty first
            Faculty faculty = new Faculty();
            faculty.setFacultyName(batchDTO.getFacultyName());
            faculty.setEmail(batchDTO.getFacultyEmail());
            faculty.setDepartment(batchDTO.getDepartment());
            Faculty savedFaculty = facultyRepo.save(faculty);

            // Log after saving faculty
            logger.info("Faculty saved with ID: {}", savedFaculty.getFacultyId());

            // Create Venue
            Venue venue = new Venue();
            venue.setVenueName(batchDTO.getVenueName());
            venue.setLocation(batchDTO.getVenueLocation());
            venue.setCapacity(batchDTO.getCapacity());
            Venue savedVenue = venueRepo.save(venue);

            // Log after saving venue
            logger.info("Venue saved with ID: {}", savedVenue.getVenueId());

            // Create Batch with saved Faculty and Venue
            Batch batch = new Batch();
            batch.setBatchName(batchDTO.getBatchName());
            batch.setStartDate(batchDTO.getStartDate());
            batch.setEndDate(batchDTO.getEndDate());
            batch.setFaculty(savedFaculty);
            batch.setVenue(savedVenue);

            Batch savedBatch = batchRepo.save(batch);

            // Log after saving batch
            logger.info("Batch saved with ID: {}", savedBatch.getBatchId());

            return ResponseEntity.ok(convertToDTO(savedBatch));
        } catch (Exception e) {
            // Log error and return error response
            logger.error("Error creating batch: {}", e.getMessage(), e);
            return ResponseEntity.status(500).body(null);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<BatchDTO> getBatchById(@PathVariable Integer id) {
        Batch batch = batchRepo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Batch not found"));
        return ResponseEntity.ok(convertToDTO(batch));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    @Transactional
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
