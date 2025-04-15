package com.example.demo.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "Faculties")
public class Faculty {
    
    @Id
    private int facultyId;         // Primary key

    private String facultyName;    // Faculty name
    private String department;      // Department of the faculty
    private String email;           // Email of the faculty

    // Default constructor required by Hibernate
    public Faculty() {
    }

    // Parameterized constructor
    public Faculty(int facultyId, String facultyName, String department, String email) {
        this.facultyId = facultyId;
        this.facultyName = facultyName;
        this.department = department;
        this.email = email;
    }

    // Getters and setters
    public int getFacultyId() {
        return facultyId;
    }

    public void setFacultyId(int facultyId) {
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
}
