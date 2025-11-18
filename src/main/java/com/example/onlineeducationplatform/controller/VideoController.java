package com.example.onlineeducationplatform.controller;

import com.example.onlineeducationplatform.model.Video;
import com.example.onlineeducationplatform.service.VideoService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class VideoController {
    private final VideoService videoService;

    public VideoController(VideoService videoService) {
        this.videoService = videoService;
    }

    @GetMapping("/courses/{courseId}/videos")
    public List<Video> listByCourse(@PathVariable Integer courseId) {
        return videoService.getByCourse(courseId);
    }

    @GetMapping("/videos/{id}")
    public ResponseEntity<Video> get(@PathVariable Integer id) {
        Video v = videoService.getById(id);
        return v == null ? ResponseEntity.notFound().build() : ResponseEntity.ok(v);
    }

    @PostMapping("/videos")
    public ResponseEntity<Video> create(@RequestBody Video video) {
        int rows = videoService.create(video);
        return rows > 0 ? new ResponseEntity<>(video, HttpStatus.CREATED)
                : new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    @PutMapping("/videos/{id}")
    public ResponseEntity<Video> update(@PathVariable Integer id, @RequestBody Video video) {
        video.setId(id);
        int rows = videoService.update(video);
        return rows > 0 ? ResponseEntity.ok(video) : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @DeleteMapping("/videos/{id}")
    public ResponseEntity<Void> delete(@PathVariable Integer id) {
        int rows = videoService.delete(id);
        return rows > 0 ? new ResponseEntity<>(HttpStatus.NO_CONTENT) : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}
