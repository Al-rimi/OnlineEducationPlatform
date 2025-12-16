package com.example.onlineeducationplatform.controller;

import com.example.onlineeducationplatform.model.Question;
import com.example.onlineeducationplatform.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/questions")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class QuestionController {
    @Autowired
    private QuestionService questionService;

    @GetMapping("/quiz/{quizId}")
    public List<Question> getQuestionsByQuiz(@PathVariable int quizId) {
        return questionService.getQuestionsByQuizId(quizId);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Question> getQuestion(@PathVariable int id) {
        Question question = questionService.getQuestionById(id);
        return question != null ? ResponseEntity.ok(question) : ResponseEntity.notFound().build();
    }

    @PostMapping
    @PreAuthorize("hasRole('TEACHER') or hasRole('ADMIN')")
    public ResponseEntity<Question> createQuestion(@RequestBody Question question) {
        int result = questionService.createQuestion(question);
        return result > 0 ? ResponseEntity.ok(question) : ResponseEntity.badRequest().build();
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('TEACHER') or hasRole('ADMIN')")
    public ResponseEntity<Question> updateQuestion(@PathVariable int id, @RequestBody Question question) {
        question.setId(id);
        int result = questionService.updateQuestion(question);
        return result > 0 ? ResponseEntity.ok(question) : ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('TEACHER') or hasRole('ADMIN')")
    public ResponseEntity<Void> deleteQuestion(@PathVariable int id) {
        int result = questionService.deleteQuestion(id);
        return result > 0 ? ResponseEntity.noContent().build() : ResponseEntity.notFound().build();
    }
}