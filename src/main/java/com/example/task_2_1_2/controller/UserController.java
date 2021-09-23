package com.example.task_2_1_2.controller;

import com.example.task_2_1_2.entity.User;
import com.example.task_2_1_2.message.ResponseApi;
import com.example.task_2_1_2.payload.UserDto;
import com.example.task_2_1_2.service.UserService;
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
@RequestMapping("/api/user")
public class UserController {
    @Autowired
    private UserService userService;

    /**
     * Creating new user
     * @param userDto - username, password
     * @return ResponseApi - result message, success bit while creating
     */
    @PostMapping
    public ResponseEntity<ResponseApi> create(@Valid @RequestBody UserDto userDto){
        ResponseApi responseApi = userService.create(userDto);
        return ResponseEntity.status(responseApi.isSuccess() ? HttpStatus.CREATED : HttpStatus.CONFLICT).body(responseApi);
    }

    /**
     * Reading all users
     * @return List<User>
     */
    @GetMapping
    public ResponseEntity<List<User>> getAll(){
        List<User> all = userService.getAll();
        return ResponseEntity.status(all.isEmpty() ? HttpStatus.NO_CONTENT : HttpStatus.FOUND).body(all);
    }

    /**
     * Reading user by id
     * @param id - user id
     * @return ResponseApi - result message, success bit, User object while reading
     */
    @GetMapping("/{id}")
    public ResponseEntity<ResponseApi> getById(@PathVariable Integer id){
        ResponseApi responseApi = userService.getById(id);
        return ResponseEntity.status(responseApi.isSuccess() ? HttpStatus.FOUND : HttpStatus.BAD_REQUEST).body(responseApi);
    }

    /**
     * Updating user by id
     * @param id - user id
     * @param userDto - username, password
     * @return ResponseApi - result message, success bit while updating
     */
    @PutMapping("/{id}")
    public ResponseEntity<ResponseApi> update( @PathVariable Integer id, @Valid @RequestBody UserDto userDto){
        ResponseApi responseApi = userService.update(id, userDto);
        return ResponseEntity.status(responseApi.isSuccess() ? HttpStatus.ACCEPTED : HttpStatus.CONFLICT).body(responseApi);
    }

    /**
     * Deleting user by id
     * @param id - user id
     * @return ResponseApi - result message, success bit while deleting
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<ResponseApi> deleteById(@PathVariable Integer id){
        ResponseApi responseApi = userService.deleteById(id);
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
