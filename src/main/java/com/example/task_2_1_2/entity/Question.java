package com.example.task_2_1_2.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Question {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false)
    private String header;

    @Column(nullable = false)
    private String content;

    @Column(nullable = false)
    private String solution;

    @ManyToOne
    private Theme theme;
}
