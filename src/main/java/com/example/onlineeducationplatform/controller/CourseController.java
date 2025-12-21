package com.example.onlineeducationplatform.controller;

import com.example.onlineeducationplatform.model.Course;
import com.example.onlineeducationplatform.model.User;
import com.example.onlineeducationplatform.service.CourseService;
import com.example.onlineeducationplatform.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.access.prepost.PreAuthorize;
import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/api/courses")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class CourseController {
    private final CourseService courseService;
    private final UserService userService;

    public CourseController(CourseService courseService, UserService userService) {
        this.courseService = courseService;
        this.userService = userService;
    }

    @GetMapping
    public List<Course> list() {
        return courseService.getAll();
    }

    @GetMapping("/search")
    public List<Course> search(@RequestParam("keyword") String keyword) {
        return courseService.search(keyword);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Course> get(@PathVariable Integer id) {
        Course c = courseService.getById(id);
        return c == null ? ResponseEntity.notFound().build() : ResponseEntity.ok(c);
    }

    @GetMapping("/category/{categoryId}")
    public List<Course> byCategory(@PathVariable Integer categoryId) {
        return courseService.getByCategory(categoryId);
    }

    @GetMapping("/enrolled")
    public ResponseEntity<List<Course>> getEnrolled() {
        User current = getCurrentUser();
        if (current == null)
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        List<Course> enrolled = courseService.getEnrolledCourses(current.getId());
        return ResponseEntity.ok(enrolled);
    }

    @GetMapping("/my-courses")
    @PreAuthorize("hasRole('TEACHER') or hasRole('ADMIN')")
    public ResponseEntity<List<Course>> getMyCourses() {
        User current = getCurrentUser();
        if (current == null)
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        if (!hasAnyRole(current, "ADMIN", "TEACHER"))
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        List<Course> courses = courseService.getByTeacher(current.getId());
        return ResponseEntity.ok(courses);
    }

    @PostMapping
    @PreAuthorize("hasRole('TEACHER') or hasRole('ADMIN')")
    public ResponseEntity<Course> create(@RequestBody Course course) {
        User current = getCurrentUser();
        if (current == null)
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        if (!hasAnyRole(current, "ADMIN", "TEACHER"))
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        // set defaults and ownership
        course.setCreatedBy(current.getId());
        if (course.getStatus() == null)
            course.setStatus("PUBLISHED");
        if (course.getCurrency() == null)
            course.setCurrency("USD");
        if (course.getVisibility() == null)
            course.setVisibility("PUBLIC");
        int rows = courseService.create(course);
        return rows > 0 ? new ResponseEntity<>(course, HttpStatus.CREATED)
                : new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('TEACHER') or hasRole('ADMIN')")
    public ResponseEntity<Course> update(@PathVariable Integer id, @RequestBody Course course) {
        course.setId(id);
        User current = getCurrentUser();
        if (current == null)
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        if (!hasAnyRole(current, "ADMIN", "TEACHER"))
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        int rows = courseService.update(course);
        return rows > 0 ? ResponseEntity.ok(course) : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('TEACHER') or hasRole('ADMIN')")
    public ResponseEntity<Void> delete(@PathVariable Integer id) {
        User current = getCurrentUser();
        if (current == null)
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        if (!hasAnyRole(current, "ADMIN", "TEACHER"))
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        int rows = courseService.delete(id);
        return rows > 0 ? new ResponseEntity<>(HttpStatus.NO_CONTENT) : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PostMapping("/{id}/enroll")
    @PreAuthorize("hasRole('STUDENT') or hasRole('TEACHER') or hasRole('ADMIN')")
    public ResponseEntity<Void> enroll(@PathVariable Integer id) {
        User current = getCurrentUser();
        if (current == null)
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        if (!hasAnyRole(current, "TEACHER", "STUDENT"))
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        boolean ok = courseService.enrollUser(id, current.getUsername());
        return ok ? new ResponseEntity<>(HttpStatus.NO_CONTENT) : new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    @PostMapping("/{id}/purchase")
    @PreAuthorize("hasRole('STUDENT') or hasRole('TEACHER') or hasRole('ADMIN')")
    public ResponseEntity<Void> purchase(@PathVariable Integer id) {
        User current = getCurrentUser();
        if (current == null)
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        if (!hasAnyRole(current, "TEACHER", "STUDENT"))
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        boolean ok = courseService.purchaseCourse(id, current.getUsername());
        return ok ? new ResponseEntity<>(HttpStatus.NO_CONTENT) : new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    private User getCurrentUser() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth == null || auth.getName() == null)
            return null;
        User u = userService.getUserByUsername(auth.getName());
        if (u != null)
            u.setPassword(null);
        return u;
    }

    private boolean hasAnyRole(User u, String... roles) {
        if (u == null || u.getRoles() == null)
            return false;
        Set<String> have = u.getRoles().stream().map(role -> role.getName())
                .collect(java.util.stream.Collectors.toSet());
        for (String r : roles) {
            if (have.contains(r))
                return true;
        }
        return false;
    }
}
