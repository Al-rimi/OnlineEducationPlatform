package com.example.onlineeducationplatform.controller;

import com.example.onlineeducationplatform.model.Discussion;
import com.example.onlineeducationplatform.model.DiscussionReply;
import com.example.onlineeducationplatform.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@RestController
@RequestMapping("/api/discussions")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class DiscussionController {
    private final JdbcTemplate jdbcTemplate;
    private final UserService userService;

    public DiscussionController(JdbcTemplate jdbcTemplate, UserService userService) {
        this.jdbcTemplate = jdbcTemplate;
        this.userService = userService;
    }

    @GetMapping
    public List<Discussion> list() {
        return jdbcTemplate.query("SELECT * FROM discussions ORDER BY created_at DESC", discussionMapper());
    }

    @GetMapping("/course/{courseId}")
    public List<Discussion> byCourse(@PathVariable Integer courseId) {
        return jdbcTemplate.query("SELECT * FROM discussions WHERE course_id = ? ORDER BY created_at DESC",
                discussionMapper(), courseId);
    }

    @PostMapping
    @PreAuthorize("hasRole('USER') or hasRole('TEACHER') or hasRole('ADMIN')")
    public ResponseEntity<Discussion> create(@RequestBody Discussion discussion) {
        Integer currentUserId = getCurrentUserId();
        if (currentUserId == null || discussion.getCourseId() == null || discussion.getTitle() == null
                || discussion.getContent() == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        int rows = jdbcTemplate.update("INSERT INTO discussions (course_id, user_id, title, content) VALUES (?,?,?,?)",
                discussion.getCourseId(), currentUserId, discussion.getTitle(), discussion.getContent());
        if (rows > 0) {
            discussion.setUserId(currentUserId);
            return new ResponseEntity<>(discussion, HttpStatus.CREATED);
        }
        return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @PostMapping("/{id}/reply")
    @PreAuthorize("hasRole('USER') or hasRole('TEACHER') or hasRole('ADMIN')")
    public ResponseEntity<DiscussionReply> reply(@PathVariable Integer id, @RequestBody DiscussionReply reply) {
        Integer currentUserId = getCurrentUserId();
        if (currentUserId == null || reply.getContent() == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        int rows = jdbcTemplate.update(
                "INSERT INTO discussion_replies (discussion_id, user_id, content) VALUES (?,?,?)", id, currentUserId,
                reply.getContent());
        if (rows > 0) {
            reply.setUserId(currentUserId);
            reply.setDiscussionId(id);
            return new ResponseEntity<>(reply, HttpStatus.CREATED);
        }
        return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @GetMapping("/{id}/replies")
    public List<DiscussionReply> replies(@PathVariable Integer id) {
        return jdbcTemplate.query("SELECT * FROM discussion_replies WHERE discussion_id = ? ORDER BY created_at ASC",
                replyMapper(), id);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> delete(@PathVariable Integer id) {
        int rows = jdbcTemplate.update("DELETE FROM discussions WHERE id = ?", id);
        return rows > 0 ? new ResponseEntity<>(HttpStatus.NO_CONTENT) : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    private Integer getCurrentUserId() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth == null || auth.getName() == null) {
            return null;
        }
        var u = userService.getUserByUsername(auth.getName());
        return u == null ? null : u.getId();
    }

    private RowMapper<Discussion> discussionMapper() {
        return new RowMapper<Discussion>() {
            @Override
            public Discussion mapRow(ResultSet rs, int rowNum) throws SQLException {
                Discussion d = new Discussion();
                d.setId(rs.getInt("id"));
                d.setCourseId(rs.getInt("course_id"));
                d.setUserId(rs.getInt("user_id"));
                d.setTitle(rs.getString("title"));
                d.setContent(rs.getString("content"));
                d.setCreatedAt(rs.getTimestamp("created_at"));
                return d;
            }
        };
    }

    private RowMapper<DiscussionReply> replyMapper() {
        return new RowMapper<DiscussionReply>() {
            @Override
            public DiscussionReply mapRow(ResultSet rs, int rowNum) throws SQLException {
                DiscussionReply r = new DiscussionReply();
                r.setId(rs.getInt("id"));
                r.setDiscussionId(rs.getInt("discussion_id"));
                r.setUserId(rs.getInt("user_id"));
                r.setContent(rs.getString("content"));
                r.setCreatedAt(rs.getTimestamp("created_at"));
                return r;
            }
        };
    }
}
