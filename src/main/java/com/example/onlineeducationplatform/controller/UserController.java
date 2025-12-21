// ...existing code...
package com.example.onlineeducationplatform.controller;

import com.example.onlineeducationplatform.model.User;
import com.example.onlineeducationplatform.model.Role;
import com.example.onlineeducationplatform.service.UserService;
import com.example.onlineeducationplatform.service.CourseService;
import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;
import java.util.List;
import java.util.Map;
import java.util.ArrayList;

import com.example.onlineeducationplatform.security.JwtUtil;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.access.prepost.PreAuthorize;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@RestController
@RequestMapping("/api/users")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class UserController {
    private static final Logger logger = LoggerFactory.getLogger(UserController.class);

    @Autowired
    private UserService userService;
    @Autowired
    private CourseService courseService;

    // Login endpoint
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest loginRequest) {
        User user = userService.getUserByUsername(loginRequest.getUsername());
        if (user != null && userService.checkPassword(loginRequest.getPassword(), user.getPassword())) {
            List<String> roleNames = userService.getRoleNamesByUserId(user.getId());
            String token = JwtUtil.generateToken(user.getUsername(), roleNames);
            user.setPassword(null);
            return ResponseEntity.ok().body(new AuthResponse(token, new UserPayload(user, roleNames)));
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid username or password");
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
    @PreAuthorize("hasRole('ADMIN')")
    public List<User> getAllUsers() {
        return userService.getAllUsers();
    }
    // ...existing code...

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<User> getUserById(@PathVariable Integer id) {
        User user = userService.getUserById(id);
        if (user != null) {
            return ResponseEntity.ok(user);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<User> addUser(@RequestBody User user) {
        int result = userService.addUser(user);
        if (result > 0) {
            return new ResponseEntity<>(user, HttpStatus.CREATED);
        } else {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<User> updateUser(@PathVariable Integer id, @RequestBody Map<String, Object> userData) {
        User user = new User();
        user.setId(id);
        user.setUsername((String) userData.get("username"));
        user.setEmail((String) userData.get("email"));

        // Handle password - only set if provided
        String password = (String) userData.get("password");
        if (password != null && !password.trim().isEmpty()) {
            user.setPassword(password); // Store as plain text
        }

        // Handle roles
        @SuppressWarnings("unchecked")
        List<String> roleNames = (List<String>) userData.get("roles");
        if (roleNames != null) {
            List<Role> roles = new ArrayList<>();
            for (String roleName : roleNames) {
                Role role = new Role();
                role.setName(roleName);
                roles.add(role);
            }
            user.setRoles(roles);
        }

        int result = userService.updateUser(user);
        if (result > 0) {
            return ResponseEntity.ok(user);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> deleteUser(@PathVariable Integer id) {
        int result = userService.deleteUser(id);
        if (result > 0) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    // Get current authenticated user info
    @GetMapping("/me")
    public ResponseEntity<User> me() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        logger.info("/me called, authentication: {}", auth != null ? auth.getName() : "null");
        if (auth == null || auth.getName() == null) {
            logger.warn("/me: no authentication");
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        User user = userService.getUserByUsername(auth.getName());
        logger.info("/me: found user: {}", user != null ? user.getUsername() : "null");
        if (user == null) {
            logger.warn("/me: user not found for username: {}", auth.getName());
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        // hide password
        user.setPassword(null);
        return ResponseEntity.ok(user);
    }

    // Update current user's profile
    @PutMapping("/profile")
    public ResponseEntity<?> updateProfile(@RequestBody ProfileUpdateRequest request) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth == null || auth.getName() == null)
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();

        User currentUser = userService.getUserByUsername(auth.getName());
        if (currentUser == null)
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();

        // Verify current password
        if (!userService.checkPassword(request.getCurrentPassword(), currentUser.getPassword())) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Current password is incorrect");
        }

        // Check if username is already taken by another user
        User existingUser = userService.getUserByUsername(request.getUsername());
        if (existingUser != null && !existingUser.getId().equals(currentUser.getId())) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Username already exists");
        }

        // Update user data
        currentUser.setUsername(request.getUsername());
        currentUser.setEmail(request.getEmail());

        // Update password if provided
        if (request.getNewPassword() != null && !request.getNewPassword().trim().isEmpty()) {
            currentUser.setPassword(request.getNewPassword());
        }

        int result = userService.updateUser(currentUser);
        if (result > 0) {
            // Return updated user without password
            currentUser.setPassword(null);
            return ResponseEntity.ok(currentUser);
        } else {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to update profile");
        }
    }

    // Get users enrolled in a specific course (for teachers to see their students)
    @GetMapping("/enrolled-in-course/{courseId}")
    @PreAuthorize("hasRole('TEACHER') or hasRole('ADMIN')")
    public ResponseEntity<List<User>> getUsersEnrolledInCourse(@PathVariable Integer courseId) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth == null || auth.getName() == null)
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();

        User currentUser = userService.getUserByUsername(auth.getName());
        if (currentUser == null)
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();

        // Check if the current user is the teacher of this course or an admin
        com.example.onlineeducationplatform.model.Course course = courseService.getById(courseId);
        if (course == null)
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();

        boolean isAdmin = currentUser.getRoles().stream().anyMatch(role -> "ADMIN".equals(role.getName()));
        if (!isAdmin && !course.getCreatedBy().equals(currentUser.getId()))
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();

        List<User> enrolledUsers = userService.getUsersEnrolledInCourse(courseId);
        return ResponseEntity.ok(enrolledUsers);
    }
}

class AuthResponse {
    private String token;
    private UserPayload user;

    public AuthResponse(String token, UserPayload user) {
        this.token = token;
        this.user = user;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public UserPayload getUser() {
        return user;
    }

    public void setUser(UserPayload user) {
        this.user = user;
    }
}

class UserPayload {
    private Integer id;
    private String username;
    private String email;
    private java.util.List<String> roles;

    public UserPayload(User user, java.util.List<String> roleNames) {
        this.id = user.getId();
        this.username = user.getUsername();
        this.email = user.getEmail();
        this.roles = roleNames;
    }

    public Integer getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public String getEmail() {
        return email;
    }

    public java.util.List<String> getRoles() {
        return roles;
    }
}

class LoginRequest {
    private String username;
    private String password;

    public LoginRequest() {
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}

class ProfileUpdateRequest {
    private String username;
    private String email;
    private String currentPassword;
    private String newPassword;

    public ProfileUpdateRequest() {
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getCurrentPassword() {
        return currentPassword;
    }

    public void setCurrentPassword(String currentPassword) {
        this.currentPassword = currentPassword;
    }

    public String getNewPassword() {
        return newPassword;
    }

    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
    }
}