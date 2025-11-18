package com.example.onlineeducationplatform.service;

import com.example.onlineeducationplatform.model.Video;
import java.util.List;

public interface VideoService {
    Video getById(Integer id);

    List<Video> getByCourse(Integer courseId);

    int create(Video video);

    int update(Video video);

    int delete(Integer id);
}
