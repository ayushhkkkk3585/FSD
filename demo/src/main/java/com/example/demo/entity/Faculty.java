package com.example.demo.entity;

import jakarta.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "faculties")
public class Faculty {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer facultyId;

    @Column(nullable = false)
    private String facultyName;
    
    private String department;
    
    @Column(unique = true)
    private String email;

    // One-to-Many relationship with Batch (One faculty can teach many batches)
    @OneToMany(mappedBy = "faculty", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Set<Batch> batches = new HashSet<>();

    // Getters and Setters
    public Integer getFacultyId() {
        return facultyId;
    }

    public void setFacultyId(Integer facultyId) {
        this.facultyId = facultyId;
    }

    public String getFacultyName() {
        return facultyName;
    }

    public void setFacultyName(String facultyName) {
        this.facultyName = facultyName;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Set<Batch> getBatches() {
        return batches;
    }

    public void setBatches(Set<Batch> batches) {
        this.batches = batches;
    }

    // Helper methods for bidirectional relationships
    public void addBatch(Batch batch) {
        this.batches.add(batch);
        batch.setFaculty(this);
    }

    public void removeBatch(Batch batch) {
        this.batches.remove(batch);
        batch.setFaculty(null);
    }
}