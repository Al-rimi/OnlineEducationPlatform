package com.example.onlineeducationplatform.controller;

import com.example.onlineeducationplatform.model.Option;
import com.example.onlineeducationplatform.service.OptionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/options")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class OptionController {
    @Autowired
    private OptionService optionService;

    @GetMapping("/question/{questionId}")
    public List<Option> getOptionsByQuestion(@PathVariable int questionId) {
        return optionService.getOptionsByQuestionId(questionId);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Option> getOption(@PathVariable int id) {
        Option option = optionService.getOptionById(id);
        return option != null ? ResponseEntity.ok(option) : ResponseEntity.notFound().build();
    }

    @PostMapping
    @PreAuthorize("hasRole('TEACHER') or hasRole('ADMIN')")
    public ResponseEntity<Option> createOption(@RequestBody Option option) {
        int result = optionService.createOption(option);
        return result > 0 ? ResponseEntity.ok(option) : ResponseEntity.badRequest().build();
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('TEACHER') or hasRole('ADMIN')")
    public ResponseEntity<Option> updateOption(@PathVariable int id, @RequestBody Option option) {
        option.setId(id);
        int result = optionService.updateOption(option);
        return result > 0 ? ResponseEntity.ok(option) : ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('TEACHER') or hasRole('ADMIN')")
    public ResponseEntity<Void> deleteOption(@PathVariable int id) {
        int result = optionService.deleteOption(id);
        return result > 0 ? ResponseEntity.noContent().build() : ResponseEntity.notFound().build();
    }
}