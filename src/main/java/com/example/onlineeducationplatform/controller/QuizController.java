package com.example.onlineeducationplatform.controller;

import com.example.onlineeducationplatform.model.Quiz;
import com.example.onlineeducationplatform.service.QuizService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/quizzes")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class QuizController {
    @Autowired
    private QuizService quizService;

    @GetMapping
    public List<Quiz> getAllQuizzes() {
        return quizService.getAllQuizzes();
    }

    @GetMapping("/course/{courseId}")
    public List<Quiz> getQuizzesByCourse(@PathVariable int courseId) {
        return quizService.getQuizzesByCourseId(courseId);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Quiz> getQuiz(@PathVariable int id) {
        Quiz quiz = quizService.getQuizById(id);
        return quiz != null ? ResponseEntity.ok(quiz) : ResponseEntity.notFound().build();
    }

    @PostMapping
    @PreAuthorize("hasRole('TEACHER') or hasRole('ADMIN')")
    public ResponseEntity<Quiz> createQuiz(@RequestBody Quiz quiz) {
        int result = quizService.createQuiz(quiz);
        return result > 0 ? ResponseEntity.ok(quiz) : ResponseEntity.badRequest().build();
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('TEACHER') or hasRole('ADMIN')")
    public ResponseEntity<Quiz> updateQuiz(@PathVariable int id, @RequestBody Quiz quiz) {
        quiz.setId(id);
        int result = quizService.updateQuiz(quiz);
        return result > 0 ? ResponseEntity.ok(quiz) : ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('TEACHER') or hasRole('ADMIN')")
    public ResponseEntity<Void> deleteQuiz(@PathVariable int id) {
        int result = quizService.deleteQuiz(id);
        return result > 0 ? ResponseEntity.noContent().build() : ResponseEntity.notFound().build();
    }
}