package com.example.onlineeducationplatform.service;

import com.example.onlineeducationplatform.model.Category;
import java.util.List;

public interface CategoryService {
    Category getById(Integer id);

    List<Category> getAll();

    int create(Category category);

    int update(Category category);

    int delete(Integer id);
}
