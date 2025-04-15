package com.example.demo.repository;

import com.example.demo.entity.Venue;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VenueRepository extends JpaRepository<Venue, Long> {
    // Additional query methods can be defined here if needed
}
