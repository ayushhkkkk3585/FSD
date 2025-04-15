package com.example.demo.repository;

import com.example.demo.entity.Allocation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AllocationRepository extends JpaRepository<Allocation, Integer> {
    // Additional query methods can be defined here if needed
}
