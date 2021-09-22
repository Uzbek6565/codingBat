package com.example.task_2_1_2.controller;

import com.example.task_2_1_2.entity.Course;
import com.example.task_2_1_2.message.ResponseApi;
import com.example.task_2_1_2.payload.CourseDto;
import com.example.task_2_1_2.service.CourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/course")
public class CourseController {
    @Autowired
    CourseService courseService;

    /**
     * Creating new course
     * @param courseDto - course name
     * @return ResponseApi - result message and status while creating
     */
    @PostMapping
    public ResponseEntity<ResponseApi> create(@Valid @RequestBody CourseDto courseDto){
        ResponseApi responseApi = courseService.create(courseDto);
        return ResponseEntity.status(responseApi.isSuccess() ? HttpStatus.CREATED : HttpStatus.BAD_REQUEST).body(responseApi);
    }

    /**
     * Reading all courses
     * @return List<Course>
     */
    @GetMapping
    public ResponseEntity<List<Course>> getAll(){
        List<Course> all = courseService.getAll();
        return ResponseEntity.status(all.isEmpty() ? HttpStatus.NO_CONTENT : HttpStatus.FOUND).body(all);
    }

    /**
     * Reading course by id
     * @param id - course id
     * @return ResponseApi - result message, status and object while reading
     */
    @GetMapping("/{id}")
    public ResponseEntity<ResponseApi> getById(@PathVariable Integer id){
        ResponseApi responseApi = courseService.getById(id);
        return ResponseEntity.status(responseApi.isSuccess() ? HttpStatus.FOUND : HttpStatus.NOT_FOUND).body(responseApi);
    }

    /**
     * Updating course by id
     * @param id - course id
     * @param courseDto - course name
     * @return ResponseApi - result message and status while updating
     */
    @PutMapping("/{id}")
    public ResponseEntity<ResponseApi> update(@PathVariable Integer id, @Valid @RequestBody CourseDto courseDto){
        ResponseApi responseApi = courseService.update(id, courseDto);
        return ResponseEntity.status(responseApi.isSuccess() ? HttpStatus.ACCEPTED : HttpStatus.BAD_REQUEST).body(responseApi);
    }

    /**
     * Deleting course by id
     * @param id - course id
     * @return ResponseApi - result message and status while deleting
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<ResponseApi> deleteById(@PathVariable Integer id){
        ResponseApi responseApi = courseService.deleteById(id);
        return ResponseEntity.status(responseApi.isSuccess() ? HttpStatus.NO_CONTENT : HttpStatus.BAD_REQUEST).body(responseApi);
    }

}
