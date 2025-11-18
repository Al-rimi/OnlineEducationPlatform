package com.example.onlineeducationplatform.controller;

import com.example.onlineeducationplatform.model.Course;
import com.example.onlineeducationplatform.service.CourseService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/courses")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class CourseController {
    private final CourseService courseService;

    public CourseController(CourseService courseService) {
        this.courseService = courseService;
    }

    @GetMapping
    public List<Course> list() {
        return courseService.getAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Course> get(@PathVariable Integer id) {
        Course c = courseService.getById(id);
        return c == null ? ResponseEntity.notFound().build() : ResponseEntity.ok(c);
    }

    @GetMapping("/category/{categoryId}")
    public List<Course> byCategory(@PathVariable Integer categoryId) {
        return courseService.getByCategory(categoryId);
    }

    @GetMapping("/search")
    public List<Course> search(@RequestParam("keyword") String keyword) {
        return courseService.search(keyword);
    }

    @PostMapping
    public ResponseEntity<Course> create(@RequestBody Course course) {
        int rows = courseService.create(course);
        return rows > 0 ? new ResponseEntity<>(course, HttpStatus.CREATED)
                : new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Course> update(@PathVariable Integer id, @RequestBody Course course) {
        course.setId(id);
        int rows = courseService.update(course);
        return rows > 0 ? ResponseEntity.ok(course) : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Integer id) {
        int rows = courseService.delete(id);
        return rows > 0 ? new ResponseEntity<>(HttpStatus.NO_CONTENT) : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}
