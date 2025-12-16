package com.example.onlineeducationplatform.controller;

import com.example.onlineeducationplatform.model.Message;
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
@RequestMapping("/api/messages")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class MessageController {
    private final JdbcTemplate jdbcTemplate;
    private final UserService userService;

    public MessageController(JdbcTemplate jdbcTemplate, UserService userService) {
        this.jdbcTemplate = jdbcTemplate;
        this.userService = userService;
    }

    @GetMapping
    public ResponseEntity<List<Message>> list() {
        Integer currentUserId = getCurrentUserId();
        if (currentUserId == null) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
        boolean isAdmin = hasRole("ADMIN");
        List<Message> messages;
        if (isAdmin) {
            messages = jdbcTemplate.query("SELECT * FROM messages ORDER BY sent_at DESC", mapper());
        } else {
            messages = jdbcTemplate.query(
                    "SELECT * FROM messages WHERE sender_id = ? OR receiver_id = ? ORDER BY sent_at DESC",
                    mapper(), currentUserId, currentUserId);
        }
        return ResponseEntity.ok(messages);
    }

    @PostMapping
    public ResponseEntity<Message> send(@RequestBody Message message) {
        Integer currentUserId = getCurrentUserId();
        if (currentUserId == null) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
        if (message.getReceiverId() == null || message.getContent() == null || message.getContent().trim().isEmpty()) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        int rows = jdbcTemplate.update(
                "INSERT INTO messages (sender_id, receiver_id, content) VALUES (?,?,?)",
                currentUserId, message.getReceiverId(), message.getContent());
        if (rows > 0) {
            message.setSenderId(currentUserId);
            return new ResponseEntity<>(message, HttpStatus.CREATED);
        }
        return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> delete(@PathVariable Integer id) {
        int rows = jdbcTemplate.update("DELETE FROM messages WHERE id = ?", id);
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

    private boolean hasRole(String role) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth == null || auth.getAuthorities() == null)
            return false;
        return auth.getAuthorities().stream().anyMatch(a -> a.getAuthority().equals("ROLE_" + role));
    }

    private RowMapper<Message> mapper() {
        return new RowMapper<Message>() {
            @Override
            public Message mapRow(ResultSet rs, int rowNum) throws SQLException {
                Message m = new Message();
                m.setId(rs.getInt("id"));
                m.setSenderId(rs.getInt("sender_id"));
                m.setReceiverId(rs.getInt("receiver_id"));
                m.setContent(rs.getString("content"));
                m.setSentAt(rs.getTimestamp("sent_at"));
                return m;
            }
        };
    }
}
