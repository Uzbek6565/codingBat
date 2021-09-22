package com.example.task_2_1_2.repository;

import com.example.task_2_1_2.entity.Question;
import org.springframework.data.jpa.repository.JpaRepository;

public interface QuestionRepository extends JpaRepository<Question, Integer> {
}
