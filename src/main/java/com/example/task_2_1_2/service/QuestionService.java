package com.example.task_2_1_2.service;

import com.example.task_2_1_2.entity.Question;
import com.example.task_2_1_2.entity.Theme;
import com.example.task_2_1_2.message.ResponseApi;
import com.example.task_2_1_2.payload.QuestionDto;
import com.example.task_2_1_2.repository.QuestionRepository;
import com.example.task_2_1_2.repository.ThemeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class QuestionService {
    @Autowired
    QuestionRepository questionRepository;

    @Autowired
    ThemeRepository themeRepository;

    /**
     * Creating new question by theme
     * @param questionDto - question header, content, solution, theme id
     * @return ResponseApi - result message and success bit while creating
     */
    public ResponseApi create(QuestionDto questionDto) {
        Optional<Theme> optionalTheme = themeRepository.findById(questionDto.getThemeId());
        if (optionalTheme.isEmpty())
            return new ResponseApi("Theme is not selected", false);
        if (questionRepository.existsByHeaderAndTheme_Name(questionDto.getHeader(), optionalTheme.get().getName()))
            return new ResponseApi("Question already exists in the theme", false);
        Question question = new Question(null, questionDto.getHeader(), questionDto.getContent(), questionDto.getSolution(), optionalTheme.get());
        questionRepository.save(question);
        return new ResponseApi("Questin is created", true);
    }

    /**
     * Reading all questions
     * @return List<Question>
     */
    public List<Question> getAll() {
        return questionRepository.findAll();
    }

    /**
     * Reading question by id
     * @param id - question id
     * @return ResponseApi - result message, success bit and Question object while reading
     */
    public ResponseApi getById(Integer id) {
        Optional<Question> optionalQuestion = questionRepository.findById(id);
        if(optionalQuestion.isEmpty())
            return new ResponseApi("Question is not found", false, null);
        return new ResponseApi("Question is found", true, optionalQuestion.get());
    }

    /**
     * Updating question by id
     * @param id - question id
     * @param questionDto - question header, content, solution, theme id
     * @return ResponseApi - result message and success bit while updating
     */
    public ResponseApi update(Integer id, QuestionDto questionDto) {
        Optional<Question> optionalQuestion = questionRepository.findById(id);
        if(optionalQuestion.isEmpty())
            return new ResponseApi("Question is not found", false);
        Optional<Theme> optionalTheme = themeRepository.findById(questionDto.getThemeId());
        if (optionalTheme.isEmpty())
            return new ResponseApi("Theme is not selected", false);
        if (questionRepository.existsByHeaderAndTheme_Name(questionDto.getHeader(), optionalTheme.get().getName()))
            return new ResponseApi("Question already exists in the theme", false);
        Question question = optionalQuestion.get();
        question.setHeader(questionDto.getHeader());
        question.setContent(questionDto.getContent());
        question.setSolution(questionDto.getSolution());
        question.setTheme(optionalTheme.get());
        questionRepository.save(question);
        return new ResponseApi("Question is updated", true);
    }

    /**
     * Deleting question by id
     * @param id - question id
     * @return ResponseApi - result message and success bit while updating
     */
    public ResponseApi deleteById(Integer id) {
        if (!questionRepository.existsById(id))
            return new ResponseApi("Question not found", false);
        return new ResponseApi("Question is deleted", true);
    }
}
