package com.example.task_2_1_2.controller;

import com.example.task_2_1_2.entity.Question;
import com.example.task_2_1_2.message.ResponseApi;
import com.example.task_2_1_2.payload.QuestionDto;
import com.example.task_2_1_2.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/question")
public class QuestionController {
    @Autowired
    QuestionService questionService;

    /**
     * Creating new question by theme
     * @param questionDto - question header, content, solution, theme id
     * @return ResponseApi - result message and success bit while creating
     */
    @PostMapping
    public ResponseEntity<ResponseApi> create(@Valid @RequestBody QuestionDto questionDto){
        ResponseApi responseApi = questionService.create(questionDto);
        return ResponseEntity.status(responseApi.isSuccess() ? HttpStatus.CREATED : HttpStatus.BAD_REQUEST).body(responseApi);
    }

    /**
     * Reading all questions
     * @return List<Question>
     */
    @GetMapping
    public ResponseEntity<List<Question>> getAll(@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "10") int size){
        List<Question> all = questionService.getAll(page, size);
        return ResponseEntity.status(all.isEmpty() ? HttpStatus.NO_CONTENT : HttpStatus.FOUND).body(all);
    }

    /**
     * Reading question by id
     * @param id - question id
     * @return ResponseApi - result message, success bit and Question object while reading
     */
    @GetMapping("/{id}")
    public ResponseEntity<ResponseApi> getById(@PathVariable Integer id){
        ResponseApi responseApi = questionService.getById(id);
        return ResponseEntity.status(responseApi.isSuccess() ? HttpStatus.FOUND : HttpStatus.BAD_REQUEST).body(responseApi);
    }

    /**
     * Updating question by id
     * @param id - question id
     * @param questionDto - question header, content, solution, theme id
     * @return ResponseApi - result message and success bit while updating
     */
    @PutMapping("/{id}")
    public ResponseEntity<ResponseApi> update(@PathVariable Integer id, @Valid @RequestBody QuestionDto questionDto){
        ResponseApi responseApi = questionService.update(id, questionDto);
        return ResponseEntity.status(responseApi.isSuccess() ? HttpStatus.ACCEPTED : HttpStatus.CONFLICT).body(responseApi);
    }

    /**
     * Deleting question by id
     * @param id - question id
     * @return ResponseApi - result message and success bit while updating
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<ResponseApi> deleteById(@PathVariable Integer id){
        ResponseApi responseApi = questionService.deleteById(id);
        return ResponseEntity.status(responseApi.isSuccess() ? HttpStatus.NO_CONTENT : HttpStatus.BAD_REQUEST).body(responseApi);
    }

    /**
     * Validation message
     * @param ex
     * @return
     */
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Map<String, String> handleValidationExceptions(
            MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });
        return errors;
    }

}
