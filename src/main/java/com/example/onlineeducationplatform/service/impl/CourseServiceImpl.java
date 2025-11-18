package com.example.onlineeducationplatform.service.impl;

import com.example.onlineeducationplatform.mapper.CourseMapper;
import com.example.onlineeducationplatform.model.Course;
import com.example.onlineeducationplatform.service.CourseService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Service
@Transactional
public class CourseServiceImpl implements CourseService {
    private final CourseMapper courseMapper;

    public CourseServiceImpl(CourseMapper courseMapper) {
        this.courseMapper = courseMapper;
    }

    @Override
    public Course getById(Integer id) {
        return courseMapper.selectById(id);
    }

    @Override
    public List<Course> getAll() {
        return courseMapper.selectAll();
    }

    @Override
    public List<Course> getByCategory(Integer categoryId) {
        return courseMapper.selectByCategory(categoryId);
    }

    @Override
    public List<Course> search(String keyword) {
        return courseMapper.searchByTitle(keyword);
    }

    @Override
    public int create(Course course) {
        return courseMapper.insert(course);
    }

    @Override
    public int update(Course course) {
        return courseMapper.update(course);
    }

    @Override
    public int delete(Integer id) {
        return courseMapper.delete(id);
    }
}
