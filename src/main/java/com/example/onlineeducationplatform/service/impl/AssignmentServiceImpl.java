package com.example.onlineeducationplatform.service.impl;

import com.example.onlineeducationplatform.mapper.AssignmentMapper;
import com.example.onlineeducationplatform.model.Assignment;
import com.example.onlineeducationplatform.service.AssignmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class AssignmentServiceImpl implements AssignmentService {
    @Autowired
    private AssignmentMapper assignmentMapper;

    @Override
    public List<Assignment> getAllAssignments() {
        return assignmentMapper.selectAllAssignments();
    }

    @Override
    public List<Assignment> getAssignmentsByCourseId(int courseId) {
        return assignmentMapper.selectAssignmentsByCourseId(courseId);
    }

    @Override
    public Assignment getAssignmentById(int id) {
        return assignmentMapper.selectAssignmentById(id);
    }

    @Override
    public int createAssignment(Assignment assignment) {
        return assignmentMapper.insertAssignment(assignment);
    }

    @Override
    public int updateAssignment(Assignment assignment) {
        return assignmentMapper.updateAssignment(assignment);
    }

    @Override
    public int deleteAssignment(int id) {
        return assignmentMapper.deleteAssignment(id);
    }
}