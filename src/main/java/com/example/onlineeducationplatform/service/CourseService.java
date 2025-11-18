package com.example.onlineeducationplatform.service;

import com.example.onlineeducationplatform.model.Course;
import java.util.List;

public interface CourseService {
    Course getById(Integer id);

    List<Course> getAll();

    List<Course> getByCategory(Integer categoryId);

    List<Course> search(String keyword);

    int create(Course course);

    int update(Course course);

    int delete(Integer id);
}
