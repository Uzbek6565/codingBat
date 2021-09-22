package com.example.task_2_1_2.payload;

import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class ThemeDto {
    @NotNull(message = "Enter theme for questions")
    private String name;

    @NotNull(message = "Select course, please")
    private Integer courseId;


}
