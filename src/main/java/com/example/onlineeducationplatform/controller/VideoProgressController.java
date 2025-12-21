package com.example.onlineeducationplatform.controller;

import com.example.onlineeducationplatform.model.VideoProgress;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.lang.NonNull;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/video-progress")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class VideoProgressController {

    private final JdbcTemplate jdbcTemplate;

    public VideoProgressController(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    private Integer getCurrentUserId() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth == null || auth.getName() == null)
            return null;
        return jdbcTemplate.queryForObject("SELECT id FROM users WHERE username = ?", Integer.class, auth.getName());
    }

    @GetMapping
    public List<VideoProgress> getUserProgress() {
        Integer userId = getCurrentUserId();
        if (userId == null)
            return List.of();
        return jdbcTemplate.query("SELECT * FROM video_progress WHERE user_id = ?", progressMapper(), userId);
    }

    @GetMapping("/{videoId}")
    public ResponseEntity<VideoProgress> getProgress(@PathVariable Integer videoId) {
        Integer userId = getCurrentUserId();
        if (userId == null)
            return ResponseEntity.status(401).build();
        List<VideoProgress> progress = jdbcTemplate.query(
                "SELECT * FROM video_progress WHERE user_id = ? AND video_id = ?", progressMapper(), userId, videoId);
        if (progress.isEmpty()) {
            return ResponseEntity.ok(null);
        }
        return ResponseEntity.ok(progress.get(0));
    }

    @PostMapping("/{videoId}")
    public ResponseEntity<Void> updateProgress(@PathVariable Integer videoId, @RequestBody VideoProgress progress) {
        Integer userId = getCurrentUserId();
        if (userId == null)
            return ResponseEntity.status(401).build();
        progress.setUserId(userId);
        progress.setVideoId(videoId);
        progress.setUpdatedAt(LocalDateTime.now());

        int count = jdbcTemplate.queryForObject(
                "SELECT COUNT(*) FROM learning_progress WHERE user_id = ? AND video_id = ?", Integer.class, userId,
                videoId);
        if (count > 0) {
            jdbcTemplate.update(
                    "UPDATE learning_progress SET progress_percent = ?, last_position_seconds = ?, updated_at = ? WHERE user_id = ? AND video_id = ?",
                    progress.getProgressPercent(), progress.getLastPositionSeconds(), progress.getUpdatedAt(), userId,
                    videoId);
        } else {
            jdbcTemplate.update(
                    "INSERT INTO learning_progress (user_id, video_id, progress_percent, last_position_seconds, updated_at) VALUES (?, ?, ?, ?, ?)",
                    userId, videoId, progress.getProgressPercent(), progress.getLastPositionSeconds(),
                    progress.getUpdatedAt());
        }
        return ResponseEntity.ok().build();
    }

    private RowMapper<VideoProgress> progressMapper() {
        return new RowMapper<VideoProgress>() {
            @Override
            public VideoProgress mapRow(@NonNull ResultSet rs, int rowNum) throws SQLException {
                VideoProgress p = new VideoProgress();
                p.setId(rs.getInt("id"));
                p.setUserId(rs.getInt("user_id"));
                p.setVideoId(rs.getInt("video_id"));
                p.setProgressPercent(rs.getDouble("progress_percent"));
                p.setLastPositionSeconds(rs.getInt("last_position_seconds"));
                p.setUpdatedAt(rs.getTimestamp("updated_at").toLocalDateTime());
                return p;
            }
        };
    }
}