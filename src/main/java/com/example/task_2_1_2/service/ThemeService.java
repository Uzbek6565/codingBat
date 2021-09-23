package com.example.task_2_1_2.service;

import com.example.task_2_1_2.entity.Course;
import com.example.task_2_1_2.entity.Theme;
import com.example.task_2_1_2.message.ResponseApi;
import com.example.task_2_1_2.payload.ThemeDto;
import com.example.task_2_1_2.repository.CourseRepository;
import com.example.task_2_1_2.repository.ThemeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ThemeService {
    @Autowired
    ThemeRepository themeRepository;

    @Autowired
    CourseRepository courseRepository;

    /**
     * Creating new theme
     * @param themeDto - theme name and course id
     * @return ResponseApi - result message and success bit while creating
     */
    public ResponseApi create(ThemeDto themeDto) {
        Optional<Course> optionalCourse = courseRepository.findById(themeDto.getCourseId());
        if (optionalCourse.isEmpty())
            return new ResponseApi("Course not found", false);
        if (themeRepository.existsByNameAndCourse_Id(themeDto.getName(), optionalCourse.get().getId()))
            return new ResponseApi("Theme is already created in the course", false);
        Theme theme = new Theme(null, themeDto.getName(), themeDto.getDescription(), optionalCourse.get());
        themeRepository.save(theme);
        return new ResponseApi("Theme is created", true);
    }

    /**
     * Reading all themes
     * @return List<Theme>
     * @param page
     * @param size
     */
    public List<Theme> getAll(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Theme> all = themeRepository.findAll(pageable);
        return all.getContent();
    }

    /**
     * Reading theme by id
     * @param id - theme id
     * @return ResponseApi - result message,success bit and Theme object while reading
     */
    public ResponseApi getById(Integer id) {
        Optional<Theme> optionalTheme = themeRepository.findById(id);
        if (optionalTheme.isEmpty())
            return new ResponseApi("Theme not found", false);
        return new ResponseApi("Theme is found", true, optionalTheme.get());
    }

    /**
     * Updating theme by id
     * @param id - theme id
     * @param themeDto - theme name, course id
     * @return ResponseApi - result message and success bit while updating
     */
    public ResponseApi update(Integer id, ThemeDto themeDto) {
        Optional<Theme> optionalTheme = themeRepository.findById(id);
        if (optionalTheme.isEmpty())
            return new ResponseApi("Theme not found", false);
        Optional<Course> optionalCourse = courseRepository.findById(themeDto.getCourseId());
        if (optionalCourse.isEmpty())
            return new ResponseApi("Course not found", false);
        if (themeRepository.existsByNameAndCourse_Id(themeDto.getName(), optionalCourse.get().getId()))
            return new ResponseApi("Theme is already created in the course", false);
        Theme theme = optionalTheme.get();
        theme.setName(themeDto.getName());
        theme.setDescription(themeDto.getDescription());
        theme.setCourse(optionalCourse.get());
        themeRepository.save(theme);
        return new ResponseApi("Theme data is updated", true);
    }

    /**
     * Deleting theme by id
     * @param id - theme id
     * @return ResponseApi - result message and success bit while deleting
     */
    public ResponseApi deleteById(Integer id) {
        if (!courseRepository.existsById(id))
            return new ResponseApi("Theme not found", false);
        themeRepository.deleteById(id);
        return new ResponseApi("Theme is deleted", true);
    }
}
