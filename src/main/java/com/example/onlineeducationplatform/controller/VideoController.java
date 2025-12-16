package com.example.onlineeducationplatform.controller;

import com.example.onlineeducationplatform.model.Video;
import com.example.onlineeducationplatform.model.Course;
import com.example.onlineeducationplatform.model.User;
import com.example.onlineeducationplatform.service.VideoService;
import com.example.onlineeducationplatform.service.UserService;
import com.example.onlineeducationplatform.mapper.CourseMapper;
import com.example.onlineeducationplatform.mapper.EnrollmentMapper;
import com.example.onlineeducationplatform.mapper.PurchaseMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import java.util.List;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class VideoController {
    private final VideoService videoService;
    private final CourseMapper courseMapper;
    private final EnrollmentMapper enrollmentMapper;
    private final PurchaseMapper purchaseMapper;
    private final UserService userService;

    public VideoController(VideoService videoService, CourseMapper courseMapper, EnrollmentMapper enrollmentMapper,
            PurchaseMapper purchaseMapper, UserService userService) {
        this.videoService = videoService;
        this.courseMapper = courseMapper;
        this.enrollmentMapper = enrollmentMapper;
        this.purchaseMapper = purchaseMapper;
        this.userService = userService;
    }

    @GetMapping("/courses/{courseId}/videos")
    public ResponseEntity<List<Video>> listByCourse(@PathVariable Integer courseId) {
        Course c = courseMapper.selectById(courseId);
        if (c == null)
            return ResponseEntity.notFound().build();
        User current = getCurrentUser();
        if (!canViewCourse(c, current))
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        return ResponseEntity.ok(videoService.getByCourse(courseId));
    }

    @GetMapping("/videos/{id}")
    public ResponseEntity<Video> get(@PathVariable Integer id) {
        Video v = videoService.getById(id);
        if (v == null)
            return ResponseEntity.notFound().build();
        Course c = courseMapper.selectById(v.getCourseId());
        if (c == null)
            return ResponseEntity.notFound().build();
        User current = getCurrentUser();
        if (!canViewCourse(c, current))
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        return ResponseEntity.ok(v);
    }

    @PostMapping("/videos")
    public ResponseEntity<Video> create(@RequestBody Video video) {
        // Only ADMIN/TEACHER or course owner can add videos
        User current = getCurrentUser();
        if (current == null)
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        if (!hasAnyRole(current, "ADMIN", "TEACHER"))
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        int rows = videoService.create(video);
        return rows > 0 ? new ResponseEntity<>(video, HttpStatus.CREATED)
                : new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    @PutMapping("/videos/{id}")
    public ResponseEntity<Video> update(@PathVariable Integer id, @RequestBody Video video) {
        video.setId(id);
        User current = getCurrentUser();
        if (current == null)
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        if (!hasAnyRole(current, "ADMIN", "TEACHER"))
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        int rows = videoService.update(video);
        return rows > 0 ? ResponseEntity.ok(video) : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @DeleteMapping("/videos/{id}")
    public ResponseEntity<Void> delete(@PathVariable Integer id) {
        User current = getCurrentUser();
        if (current == null)
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        if (!hasAnyRole(current, "ADMIN", "TEACHER"))
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        int rows = videoService.delete(id);
        return rows > 0 ? new ResponseEntity<>(HttpStatus.NO_CONTENT) : new ResponseEntity<>(HttpStatus.NOT_FOUND);
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
        for (String r : roles) {
            if (u.getRoles().contains(r))
                return true;
        }
        return false;
    }

    private boolean canViewCourse(Course c, User u) {
        // Public and free
        if (c == null)
            return false;
        java.math.BigDecimal zero = java.math.BigDecimal.ZERO;
        java.math.BigDecimal price = c.getPrice() == null ? zero : c.getPrice();
        if ("PUBLIC".equalsIgnoreCase(c.getVisibility()) && price.compareTo(zero) == 0)
            return true;

        // Creator or admin/teacher can view
        if (u != null) {
            if (u.getId() != null && c.getCreatedBy() != null && u.getId().equals(c.getCreatedBy()))
                return true;
            if (hasAnyRole(u, "ADMIN"))
                return true;
        }

        // Role-based visibility
        if ("ROLE_BASED".equalsIgnoreCase(c.getVisibility())) {
            if (u == null)
                return false;
            String need = c.getVisibleRole();
            if (need == null)
                return false;
            return hasAnyRole(u, need);
        }

        // Enrolled or purchased required
        if (u == null)
            return false;
        if (enrollmentMapper.select(u.getId(), c.getId()) != null)
            return true;
        if (purchaseMapper.select(u.getId(), c.getId()) != null)
            return true;
        return false;
    }
}
