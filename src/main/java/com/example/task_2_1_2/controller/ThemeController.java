package com.example.task_2_1_2.controller;

import com.example.task_2_1_2.entity.Theme;
import com.example.task_2_1_2.message.ResponseApi;
import com.example.task_2_1_2.payload.ThemeDto;
import com.example.task_2_1_2.service.ThemeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/theme")
public class ThemeController {
    @Autowired
    ThemeService themeService;

    /**
     * Creating new theme
     * @param themeDto - theme name and course id
     * @return ResponseApi - result message and success bit while creating
     */
    @PostMapping
    public ResponseEntity<ResponseApi> create(@Valid @RequestBody ThemeDto themeDto){
        ResponseApi responseApi = themeService.create(themeDto);
        return ResponseEntity.status(responseApi.isSuccess() ? HttpStatus.CREATED : HttpStatus.BAD_REQUEST).body(responseApi);
    }

    /**
     * Reading all themes
     * @return List<Theme>
     */
    @GetMapping
    public ResponseEntity<List<Theme>> getAll(){
        List<Theme> all = themeService.getAll();
        return ResponseEntity.status(all.isEmpty() ? HttpStatus.NO_CONTENT : HttpStatus.FOUND).body(all);
    }

    /**
     * Reading theme by id
     * @param id - theme id
     * @return ResponseApi - result message,success bit and Theme object while reading
     */
    @GetMapping("/{id}")
    public ResponseEntity<ResponseApi> getById(@PathVariable Integer id){
        ResponseApi responseApi = themeService.getById(id);
        return ResponseEntity.status(responseApi.isSuccess() ? HttpStatus.FOUND : HttpStatus.BAD_REQUEST).body(responseApi);
    }

    /**
     * Updating theme by id
     * @param id - theme id
     * @param themeDto - theme name, course id
     * @return ResponseApi - result message and success bit while updating
     */
    @PutMapping("/{id}")
    public ResponseEntity<ResponseApi> update(@PathVariable Integer id, @Valid @RequestBody ThemeDto themeDto){
        ResponseApi responseApi = themeService.update(id, themeDto);
        return ResponseEntity.status(responseApi.isSuccess() ? HttpStatus.ACCEPTED : HttpStatus.BAD_REQUEST).body(responseApi);
    }

    /**
     * Deleting theme by id
     * @param id - theme id
     * @return ResponseApi - result message and success bit while deleting
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<ResponseApi> deleteById(@PathVariable Integer id){
        ResponseApi responseApi = themeService.deleteById(id);
        return ResponseEntity.status(responseApi.isSuccess() ? HttpStatus.NO_CONTENT : HttpStatus.BAD_REQUEST).body(responseApi);
    }
}
