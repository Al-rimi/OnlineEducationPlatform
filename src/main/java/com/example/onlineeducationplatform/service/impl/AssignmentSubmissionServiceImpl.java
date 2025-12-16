package com.example.onlineeducationplatform.service.impl;

import com.example.onlineeducationplatform.mapper.AssignmentSubmissionMapper;
import com.example.onlineeducationplatform.model.AssignmentSubmission;
import com.example.onlineeducationplatform.service.AssignmentSubmissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class AssignmentSubmissionServiceImpl implements AssignmentSubmissionService {
    @Autowired
    private AssignmentSubmissionMapper submissionMapper;

    @Override
    public List<AssignmentSubmission> getSubmissionsByAssignmentId(int assignmentId) {
        return submissionMapper.selectSubmissionsByAssignmentId(assignmentId);
    }

    @Override
    public AssignmentSubmission getSubmissionById(int id) {
        return submissionMapper.selectSubmissionById(id);
    }

    @Override
    public AssignmentSubmission getSubmissionByAssignmentAndUser(int assignmentId, int userId) {
        return submissionMapper.selectSubmissionByAssignmentAndUser(assignmentId, userId);
    }

    @Override
    public int submitAssignment(AssignmentSubmission submission) {
        return submissionMapper.insertSubmission(submission);
    }

    @Override
    public int updateSubmission(AssignmentSubmission submission) {
        return submissionMapper.updateSubmission(submission);
    }

    @Override
    public int deleteSubmission(int id) {
        return submissionMapper.deleteSubmission(id);
    }
}