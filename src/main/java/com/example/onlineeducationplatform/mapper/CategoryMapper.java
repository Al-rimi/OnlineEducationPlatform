package com.example.onlineeducationplatform.mapper;

import com.example.onlineeducationplatform.model.Category;
import org.apache.ibatis.annotations.Mapper;
import java.util.List;

@Mapper
public interface CategoryMapper {
    Category selectById(Integer id);

    List<Category> selectAll();

    int insert(Category category);

    int update(Category category);

    int delete(Integer id);
}
