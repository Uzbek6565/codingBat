package com.example.task_2_1_2.repository;

import com.example.task_2_1_2.entity.Answer;
import com.example.task_2_1_2.entity.Course;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AnswerRepository extends JpaRepository<Answer, Integer> {
    boolean existsBySolutionAndQuestion_Id(String solution, Integer question_id);
}
