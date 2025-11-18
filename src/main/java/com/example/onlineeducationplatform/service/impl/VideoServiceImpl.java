package com.example.onlineeducationplatform.service.impl;

import com.example.onlineeducationplatform.mapper.VideoMapper;
import com.example.onlineeducationplatform.model.Video;
import com.example.onlineeducationplatform.service.VideoService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Service
@Transactional
public class VideoServiceImpl implements VideoService {
    private final VideoMapper videoMapper;

    public VideoServiceImpl(VideoMapper videoMapper) {
        this.videoMapper = videoMapper;
    }

    @Override
    public Video getById(Integer id) {
        return videoMapper.selectById(id);
    }

    @Override
    public List<Video> getByCourse(Integer courseId) {
        return videoMapper.selectByCourse(courseId);
    }

    @Override
    public int create(Video video) {
        return videoMapper.insert(video);
    }

    @Override
    public int update(Video video) {
        return videoMapper.update(video);
    }

    @Override
    public int delete(Integer id) {
        return videoMapper.delete(id);
    }
}
