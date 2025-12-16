package com.example.onlineeducationplatform.mapper;

import com.example.onlineeducationplatform.model.Assignment;
import java.util.List;

public interface AssignmentMapper {
    List<Assignment> selectAllAssignments();

    List<Assignment> selectAssignmentsByCourseId(int courseId);

    Assignment selectAssignmentById(int id);

    int insertAssignment(Assignment assignment);

    int updateAssignment(Assignment assignment);

    int deleteAssignment(int id);
}