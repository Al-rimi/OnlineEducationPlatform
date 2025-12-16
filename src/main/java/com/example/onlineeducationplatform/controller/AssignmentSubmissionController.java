package com.example.onlineeducationplatform.controller;

import com.example.onlineeducationplatform.model.AssignmentSubmission;
import com.example.onlineeducationplatform.service.AssignmentSubmissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/assignment-submissions")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class AssignmentSubmissionController {
    @Autowired
    private AssignmentSubmissionService submissionService;

    @GetMapping("/assignment/{assignmentId}")
    public List<AssignmentSubmission> getSubmissionsByAssignment(@PathVariable int assignmentId) {
        return submissionService.getSubmissionsByAssignmentId(assignmentId);
    }

    @GetMapping("/{id}")
    public ResponseEntity<AssignmentSubmission> getSubmission(@PathVariable int id) {
        AssignmentSubmission submission = submissionService.getSubmissionById(id);
        return submission != null ? ResponseEntity.ok(submission) : ResponseEntity.notFound().build();
    }

    @PostMapping
    @PreAuthorize("hasRole('USER') or hasRole('TEACHER') or hasRole('ADMIN')")
    public ResponseEntity<AssignmentSubmission> submitAssignment(@RequestBody AssignmentSubmission submission) {
        int result = submissionService.submitAssignment(submission);
        return result > 0 ? ResponseEntity.ok(submission) : ResponseEntity.badRequest().build();
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('TEACHER') or hasRole('ADMIN')")
    public ResponseEntity<AssignmentSubmission> updateSubmission(@PathVariable int id,
            @RequestBody AssignmentSubmission submission) {
        submission.setId(id);
        int result = submissionService.updateSubmission(submission);
        return result > 0 ? ResponseEntity.ok(submission) : ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('TEACHER') or hasRole('ADMIN')")
    public ResponseEntity<Void> deleteSubmission(@PathVariable int id) {
        int result = submissionService.deleteSubmission(id);
        return result > 0 ? ResponseEntity.noContent().build() : ResponseEntity.notFound().build();
    }
}