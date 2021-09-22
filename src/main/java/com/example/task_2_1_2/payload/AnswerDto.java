package com.example.task_2_1_2.payload;

import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class AnswerDto {
    @NotNull(message = "Enter solution for question")
    private String solution;

    @NotNull(message = "Select course, please")
    private Integer questionId;
}
