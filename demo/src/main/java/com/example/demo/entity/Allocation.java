package com.example.demo.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.util.Date;

@Entity
@Table(name = "allocations")
public class Allocation {

    @Id
    private Integer allocationId;   // Primary key
    private Integer facultyId;      // Foreign key from faculties table
    private Integer venueId;        // Foreign key from venues table
    private Integer batchId;        // Foreign key from batches table
    private Date allocationDate;    // Date field

    // Default constructor
    public Allocation() {
    }

    // Parameterized constructor
    public Allocation(Integer allocationId, Integer facultyId, Integer venueId, Integer batchId, Date allocationDate) {
        this.allocationId = allocationId;
        this.facultyId = facultyId;
        this.venueId = venueId;
        this.batchId = batchId;
        this.allocationDate = allocationDate;
    }

    // Getters and setters
    public Integer getAllocationId() {
        return allocationId;
    }

    public void setAllocationId(Integer allocationId) {
        this.allocationId = allocationId;
    }

    public Integer getFacultyId() {
        return facultyId;
    }

    public void setFacultyId(Integer facultyId) {
        this.facultyId = facultyId;
    }

    public Integer getVenueId() {
        return venueId;
    }

    public void setVenueId(Integer venueId) {
        this.venueId = venueId;
    }

    public Integer getBatchId() {
        return batchId;
    }

    public void setBatchId(Integer batchId) {
        this.batchId = batchId;
    }

    public Date getAllocationDate() {
        return allocationDate;
    }

    public void setAllocationDate(Date allocationDate) {
        this.allocationDate = allocationDate;
    }
}
