package com.example.onlineeducationplatform.service;

import com.example.onlineeducationplatform.model.Course;
import java.util.List;

public interface CourseService {
    Course getById(Integer id);

    List<Course> getAll();

    List<Course> getByCategory(Integer categoryId);

    List<Course> search(String keyword);

    List<Course> getEnrolledCourses(Integer userId);

    List<Course> getByTeacher(Integer teacherId);

    int create(Course course);

    int update(Course course);

    int delete(Integer id);

    // Enrollment and purchase actions
    boolean enrollUser(Integer courseId, String username);

    boolean purchaseCourse(Integer courseId, String username);
}
