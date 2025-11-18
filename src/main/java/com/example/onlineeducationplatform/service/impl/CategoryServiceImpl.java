package com.example.onlineeducationplatform.service.impl;

import com.example.onlineeducationplatform.mapper.CategoryMapper;
import com.example.onlineeducationplatform.model.Category;
import com.example.onlineeducationplatform.service.CategoryService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Service
@Transactional
public class CategoryServiceImpl implements CategoryService {
    private final CategoryMapper categoryMapper;

    public CategoryServiceImpl(CategoryMapper categoryMapper) {
        this.categoryMapper = categoryMapper;
    }

    @Override
    public Category getById(Integer id) {
        return categoryMapper.selectById(id);
    }

    @Override
    public List<Category> getAll() {
        return categoryMapper.selectAll();
    }

    @Override
    public int create(Category category) {
        return categoryMapper.insert(category);
    }

    @Override
    public int update(Category category) {
        return categoryMapper.update(category);
    }

    @Override
    public int delete(Integer id) {
        return categoryMapper.delete(id);
    }
}
