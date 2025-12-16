package com.example.onlineeducationplatform.mapper;

import com.example.onlineeducationplatform.model.Course;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import java.util.List;

@Mapper
public interface CourseMapper {
    Course selectById(Integer id);

    List<Course> selectAll();

    int insert(Course course);

    int update(Course course);

    int delete(Integer id);

    List<Course> selectByCategory(@Param("categoryId") Integer categoryId);

    List<Course> searchByTitle(@Param("keyword") String keyword);

    List<Course> selectEnrolledCourses(@Param("userId") Integer userId);
}
