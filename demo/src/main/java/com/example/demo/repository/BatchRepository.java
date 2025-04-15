package com.example.demo.repository;

import com.example.demo.entity.Batch;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BatchRepository extends JpaRepository<Batch, Integer> {
    // Additional query methods can be defined here if needed
}
    