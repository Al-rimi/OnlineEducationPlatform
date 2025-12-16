package com.example.onlineeducationplatform.service;

import com.example.onlineeducationplatform.model.AssignmentSubmission;
import java.util.List;

public interface AssignmentSubmissionService {
    List<AssignmentSubmission> getSubmissionsByAssignmentId(int assignmentId);

    AssignmentSubmission getSubmissionById(int id);

    AssignmentSubmission getSubmissionByAssignmentAndUser(int assignmentId, int userId);

    int submitAssignment(AssignmentSubmission submission);

    int updateSubmission(AssignmentSubmission submission);

    int deleteSubmission(int id);
}