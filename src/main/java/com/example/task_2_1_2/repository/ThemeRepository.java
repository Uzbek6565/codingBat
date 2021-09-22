package com.example.task_2_1_2.repository;

import com.example.task_2_1_2.entity.Course;
import com.example.task_2_1_2.entity.Theme;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ThemeRepository extends JpaRepository<Theme, Integer> {
    boolean existsByNameAndCourse_Id(String name, Integer course_id);
}
