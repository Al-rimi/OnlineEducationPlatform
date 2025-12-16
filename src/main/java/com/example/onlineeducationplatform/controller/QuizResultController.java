package com.example.onlineeducationplatform.controller;

import com.example.onlineeducationplatform.model.QuizResult;
import com.example.onlineeducationplatform.service.QuizResultService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/quiz-results")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class QuizResultController {
    @Autowired
    private QuizResultService resultService;

    @GetMapping("/quiz/{quizId}")
    public List<QuizResult> getResultsByQuiz(@PathVariable int quizId) {
        return resultService.getResultsByQuizId(quizId);
    }

    @GetMapping("/{id}")
    public ResponseEntity<QuizResult> getResult(@PathVariable int id) {
        QuizResult result = resultService.getResultById(id);
        return result != null ? ResponseEntity.ok(result) : ResponseEntity.notFound().build();
    }

    @PostMapping
    @PreAuthorize("hasRole('USER') or hasRole('TEACHER') or hasRole('ADMIN')")
    public ResponseEntity<QuizResult> saveResult(@RequestBody QuizResult result) {
        int res = resultService.saveResult(result);
        return res > 0 ? ResponseEntity.ok(result) : ResponseEntity.badRequest().build();
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('TEACHER') or hasRole('ADMIN')")
    public ResponseEntity<QuizResult> updateResult(@PathVariable int id, @RequestBody QuizResult result) {
        result.setId(id);
        int res = resultService.updateResult(result);
        return res > 0 ? ResponseEntity.ok(result) : ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('TEACHER') or hasRole('ADMIN')")
    public ResponseEntity<Void> deleteResult(@PathVariable int id) {
        int res = resultService.deleteResult(id);
        return res > 0 ? ResponseEntity.noContent().build() : ResponseEntity.notFound().build();
    }
}