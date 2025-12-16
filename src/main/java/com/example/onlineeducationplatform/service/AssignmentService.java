package com.example.onlineeducationplatform.service;

import com.example.onlineeducationplatform.model.Assignment;
import java.util.List;

public interface AssignmentService {
    List<Assignment> getAllAssignments();

    List<Assignment> getAssignmentsByCourseId(int courseId);

    Assignment getAssignmentById(int id);

    int createAssignment(Assignment assignment);

    int updateAssignment(Assignment assignment);

    int deleteAssignment(int id);
}