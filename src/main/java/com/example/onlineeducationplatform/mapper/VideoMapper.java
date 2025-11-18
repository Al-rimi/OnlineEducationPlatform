package com.example.onlineeducationplatform.mapper;

import com.example.onlineeducationplatform.model.Video;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import java.util.List;

@Mapper
public interface VideoMapper {
    Video selectById(Integer id);

    List<Video> selectByCourse(@Param("courseId") Integer courseId);

    int insert(Video video);

    int update(Video video);

    int delete(Integer id);
}
