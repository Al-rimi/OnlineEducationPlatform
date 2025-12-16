package com.example.onlineeducationplatform.mapper;

import com.example.onlineeducationplatform.model.Enrollment;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import java.util.List;

@Mapper
public interface EnrollmentMapper {
    int insert(@Param("userId") Integer userId, @Param("courseId") Integer courseId);

    Enrollment select(@Param("userId") Integer userId, @Param("courseId") Integer courseId);

    List<Enrollment> selectByUser(@Param("userId") Integer userId);
}
