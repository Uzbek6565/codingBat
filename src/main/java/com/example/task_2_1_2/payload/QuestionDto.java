package com.example.task_2_1_2.payload;

import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class QuestionDto {
    @NotNull(message = "Enter question header")
    private String header;

    @NotNull(message = "Enter question content")
    private String content;

    @NotNull(message = "Enter answer for the question")
    private String solution;

    @NotNull(message = "Select theme for question, please")
    private Integer themeId;
}
