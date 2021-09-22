package com.example.task_2_1_2.service;

import com.example.task_2_1_2.entity.Course;
import com.example.task_2_1_2.message.ResponseApi;
import com.example.task_2_1_2.payload.CourseDto;
import com.example.task_2_1_2.repository.CourseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CourseService {
    @Autowired
    CourseRepository courseRepository;

    /**
     * Creating new course
     * @param courseDto - course name
     * @return ResponseApi - result message and status while creating
     */
    public ResponseApi create(CourseDto courseDto) {
        if (courseRepository.existsByName(courseDto.getName()))
            return new ResponseApi("Course already exists", false);
        Course course = new Course(null, courseDto.getName());
        courseRepository.save(course);
        return new ResponseApi("Course is created", true);
    }

    /**
     * Reading all courses
     * @return List<Course>
     */
    public List<Course> getAll() {
        return courseRepository.findAll();
    }

    /**
     * Reading course by id
     * @param id - course id
     * @return ResponseApi - result message, status and object while reading
     */
    public ResponseApi getById(Integer id) {
        Optional<Course> optionalCourse = courseRepository.findById(id);
        if (optionalCourse.isEmpty())
            return new ResponseApi("Course not found", false, null);
        return new ResponseApi("Course is found", true, optionalCourse.get());
    }

    /**
     * Updating course by id
     * @param id - course id
     * @param courseDto - course name
     * @return ResponseApi - result message and status while updating
     */
    public ResponseApi update(Integer id, CourseDto courseDto) {
        Optional<Course> optionalCourse = courseRepository.findById(id);
        if (optionalCourse.isEmpty())
            return new ResponseApi("Course not found", false, null);
        if (courseRepository.existsByName(courseDto.getName()))
            return new ResponseApi("Course already exists", false);
        Course course = optionalCourse.get();
        course.setName(courseDto.getName());
        courseRepository.save(course);
        return new ResponseApi("Course data is updated", true);
    }

    /**
     * Deleting course by id
     * @param id - course id
     * @return ResponseApi - result message and status while deleting
     */
    public ResponseApi deleteById(Integer id) {
        Optional<Course> optionalCourse = courseRepository.findById(id);
        if (optionalCourse.isEmpty())
            return new ResponseApi("Course not found", false, null);
        courseRepository.deleteById(id);
        return new ResponseApi("Course is deleted", true);
    }
}
