package com.example.demo.repository;

import com.example.demo.entity.Course;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CourseRepository extends JpaRepository<Course, Long> {
    
    Optional<Course> findByCourseName(String courseName);
    
    List<Course> findByCredits(Integer credits);
    
    @Query("SELECT c FROM Course c LEFT JOIN FETCH c.students WHERE c.courseId = :courseId")
    Optional<Course> findByIdWithStudents(@Param("courseId") Long courseId);
    
    @Query("SELECT c FROM Course c LEFT JOIN FETCH c.batches WHERE c.courseId = :courseId")
    Optional<Course> findByIdWithBatches(@Param("courseId") Long courseId);
}