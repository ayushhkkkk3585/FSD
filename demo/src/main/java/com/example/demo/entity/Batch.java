package com.example.demo.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "Batches")
public class Batch {

    @Id
    private Integer batchId; // Primary key

    private String batchName; // Batch name
    private String startDate; // Start date of the batch
    private String endDate; // End date of the batch

    // Default constructor required by Hibernate
    public Batch() {
    }

    @ManyToOne
    @JoinColumn(name = "faculty_id")
    private Faculty faculty;

    @ManyToOne
    @JoinColumn(name = "venue_id")
    private Venue venue;

    // Parameterized constructor
    public Batch(Integer batchId, String batchName, String startDate, String endDate) {
        this.batchId = batchId;
        this.batchName = batchName;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    // Getters and setters
    public Integer getBatchId() {
        return batchId;
    }

    public void setBatchId(Integer batchId) {
        this.batchId = batchId;
    }

    public String getBatchName() {
        return batchName;
    }

    public void setBatchName(String batchName) {
        this.batchName = batchName;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public Faculty getFaculty() {
        return faculty;
    }

    public void setFaculty(Faculty faculty) {
        this.faculty = faculty;
    }

    public Venue getVenue() {
        return venue;
    }

    public void setVenue(Venue venue) {
        this.venue = venue;
    }
}
