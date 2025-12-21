package com.example.onlineeducationplatform.controller;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/stats")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class StatsController {
    private final JdbcTemplate jdbcTemplate;

    public StatsController(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Map<String, Object>> getStats() {
        Map<String, Object> stats = new HashMap<>();
        stats.put("users", jdbcTemplate.queryForObject("SELECT COUNT(*) FROM users", Integer.class));
        stats.put("courses", jdbcTemplate.queryForObject("SELECT COUNT(*) FROM courses", Integer.class));
        stats.put("videos", jdbcTemplate.queryForObject("SELECT COUNT(*) FROM videos", Integer.class));
        stats.put("assignments", jdbcTemplate.queryForObject("SELECT COUNT(*) FROM assignments", Integer.class));
        stats.put("quizzes", jdbcTemplate.queryForObject("SELECT COUNT(*) FROM quizzes", Integer.class));
        stats.put("messages", jdbcTemplate.queryForObject("SELECT COUNT(*) FROM messages", Integer.class));
        stats.put("discussions", jdbcTemplate.queryForObject("SELECT COUNT(*) FROM discussions", Integer.class));
        stats.put("enrollments", jdbcTemplate.queryForObject("SELECT COUNT(*) FROM enrollments", Integer.class));
        return new ResponseEntity<>(stats, HttpStatus.OK);
    }
}
