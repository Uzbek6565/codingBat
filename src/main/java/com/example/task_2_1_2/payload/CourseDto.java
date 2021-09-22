package com.example.task_2_1_2.payload;

import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class CourseDto {
    @NotNull(message = "Enter course name")
    private String name;
}
