package com.example.onlineeducationplatform.controller;

import com.example.onlineeducationplatform.model.Assignment;
import com.example.onlineeducationplatform.service.AssignmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/assignments")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class AssignmentController {
    @Autowired
    private AssignmentService assignmentService;

    @GetMapping
    public List<Assignment> getAllAssignments() {
        return assignmentService.getAllAssignments();
    }

    @GetMapping("/course/{courseId}")
    public List<Assignment> getAssignmentsByCourse(@PathVariable int courseId) {
        return assignmentService.getAssignmentsByCourseId(courseId);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Assignment> getAssignment(@PathVariable int id) {
        Assignment assignment = assignmentService.getAssignmentById(id);
        return assignment != null ? ResponseEntity.ok(assignment) : ResponseEntity.notFound().build();
    }

    @PostMapping
    @PreAuthorize("hasRole('TEACHER') or hasRole('ADMIN')")
    public ResponseEntity<Assignment> createAssignment(@RequestBody Assignment assignment) {
        int result = assignmentService.createAssignment(assignment);
        return result > 0 ? ResponseEntity.ok(assignment) : ResponseEntity.badRequest().build();
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('TEACHER') or hasRole('ADMIN')")
    public ResponseEntity<Assignment> updateAssignment(@PathVariable int id, @RequestBody Assignment assignment) {
        assignment.setId(id);
        int result = assignmentService.updateAssignment(assignment);
        return result > 0 ? ResponseEntity.ok(assignment) : ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('TEACHER') or hasRole('ADMIN')")
    public ResponseEntity<Void> deleteAssignment(@PathVariable int id) {
        int result = assignmentService.deleteAssignment(id);
        return result > 0 ? ResponseEntity.noContent().build() : ResponseEntity.notFound().build();
    }
}