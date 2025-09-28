// ...existing code...
package com.example.onlineeducationplatform.controller;

import com.example.onlineeducationplatform.model.User;
import com.example.onlineeducationplatform.service.UserService;
import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;
import java.util.List;

@RestController
@RequestMapping("/api/users")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class UserController {
    @Autowired
    private UserService userService;

    // Login endpoint
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody User loginUser) {
        User user = userService.getUserByUsername(loginUser.getUsername());
        if (user != null && userService.checkPassword(loginUser.getPassword(), user.getPassword())) {
            // Generate JWT token
            String token = JwtUtil.generateToken(user.getUsername());
            return ResponseEntity.ok().body(new AuthResponse(token));
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid username or password");
        }
    }

    // Register endpoint
    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody User user) {
        if (userService.getUserByUsername(user.getUsername()) != null) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Username already exists");
        }
        userService.registerUser(user);
        return ResponseEntity.status(HttpStatus.CREATED).body("User registered successfully");
    }

    @GetMapping
    public List<User> getAllUsers() {
        return userService.getAllUsers();
    }
    // ...existing code...

    @GetMapping("/{id}")
    public ResponseEntity<User> getUserById(@PathVariable Integer id) {
        User user = userService.getUserById(id);
        if (user != null) {
            return ResponseEntity.ok(user);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    public ResponseEntity<User> addUser(@RequestBody User user) {
        int result = userService.addUser(user);
        if (result > 0) {
            return new ResponseEntity<>(user, HttpStatus.CREATED);
        } else {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<User> updateUser(@PathVariable Integer id, @RequestBody User user) {
        user.setId(id);
        int result = userService.updateUser(user);
        if (result > 0) {
            return ResponseEntity.ok(user);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Integer id) {
        int result = userService.deleteUser(id);
        if (result > 0) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}

// JWT utility and AuthResponse class for token handling
class JwtUtil {
    private static final String SECRET_KEY = "secretKey123456";

    public static String generateToken(String username) {
        long nowMillis = System.currentTimeMillis();
        long expMillis = nowMillis + 24 * 60 * 60 * 1000; // 1 day
        return io.jsonwebtoken.Jwts.builder()
                .setSubject(username)
                .setIssuedAt(new java.util.Date(nowMillis))
                .setExpiration(new java.util.Date(expMillis))
                .signWith(io.jsonwebtoken.SignatureAlgorithm.HS256, SECRET_KEY)
                .compact();
    }
}

class AuthResponse {
    private String token;

    public AuthResponse(String token) {
        this.token = token;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}