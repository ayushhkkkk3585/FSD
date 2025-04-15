package com.example.demo.repository;

import com.example.demo.entity.Faculty;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FacultyRepository extends JpaRepository<Faculty, Integer> {
    // Additional query methods can be defined here if needed
}
