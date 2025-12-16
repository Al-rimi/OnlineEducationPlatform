package com.example.onlineeducationplatform.mapper;

import com.example.onlineeducationplatform.model.AssignmentSubmission;
import java.util.List;

public interface AssignmentSubmissionMapper {
    List<AssignmentSubmission> selectSubmissionsByAssignmentId(int assignmentId);

    AssignmentSubmission selectSubmissionById(int id);

    AssignmentSubmission selectSubmissionByAssignmentAndUser(int assignmentId, int userId);

    int insertSubmission(AssignmentSubmission submission);

    int updateSubmission(AssignmentSubmission submission);

    int deleteSubmission(int id);
}