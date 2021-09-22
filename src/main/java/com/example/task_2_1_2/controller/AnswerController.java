package com.example.task_2_1_2.controller;

import com.example.task_2_1_2.entity.Answer;
import com.example.task_2_1_2.entity.Question;
import com.example.task_2_1_2.message.ResponseApi;
import com.example.task_2_1_2.payload.AnswerDto;
import com.example.task_2_1_2.service.AnswerService;
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
@RequestMapping("/api/answer")
public class AnswerController {
    @Autowired
    AnswerService answerService;

    /**
     * Creating new answer
     * @param answerDto - solution, question id
     * @return ResponseApi - result message and success bit while creating
     */
    @PostMapping
    public ResponseEntity<ResponseApi> create(@Valid @RequestBody AnswerDto answerDto){
        ResponseApi responseApi = answerService.create(answerDto);
        return ResponseEntity.status(responseApi.isSuccess() ? HttpStatus.CREATED :HttpStatus.CONFLICT).body(responseApi);
    }

    /**
     * Reading all answers
     * @return List<Answer>
     */
    @GetMapping
    public ResponseEntity<List<Answer>> getAll(){
        return ResponseEntity.status(answerService.getAll().isEmpty() ? HttpStatus.NO_CONTENT : HttpStatus.FOUND).body(answerService.getAll());
    }

    /**
     * Reading answer by id
     * @param id - answer id
     * @return ResponseApi - result message, success bit, Answer object while reading
     */
    @GetMapping("/{id}")
    public ResponseEntity<ResponseApi> getById(@PathVariable Integer id){
        ResponseApi responseApi = answerService.getById(id);
        return ResponseEntity.status(responseApi.isSuccess() ? HttpStatus.FOUND :HttpStatus.BAD_REQUEST).body(responseApi);
    }

    /**
     * Updatind answer by id
     * @param id - answer id
     * @param answerDto - solution, question id
     * @return ResponseApi - result message and success bit while updating
     */
    @PutMapping("/{id}")
    public ResponseEntity<ResponseApi> update(@PathVariable Integer id, @Valid @RequestBody AnswerDto answerDto){
        ResponseApi responseApi = answerService.update(id, answerDto);
        return ResponseEntity.status(responseApi.isSuccess() ? HttpStatus.ACCEPTED :HttpStatus.CONFLICT).body(responseApi);
    }

    /**
     *
     * @param id - answer id
     * @return ResponseApi - result message and success bit while deleting
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<ResponseApi> deleteById(@PathVariable Integer id){
        ResponseApi responseApi = answerService.deleteById(id);
        return ResponseEntity.status(responseApi.isSuccess() ? HttpStatus.NO_CONTENT :HttpStatus.BAD_REQUEST).body(responseApi);
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
