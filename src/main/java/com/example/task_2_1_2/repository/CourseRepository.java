package com.example.task_2_1_2.repository;

import com.example.task_2_1_2.entity.Course;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CourseRepository extends JpaRepository<Course, Integer> {
    boolean existsByName(String name);
}
