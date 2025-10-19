package com.edusmart.service;

import com.edusmart.dto.CategoryDTO;

import java.util.List;

public interface CategoryService {
    CategoryDTO createCategory(CategoryDTO category);
    List<CategoryDTO> getAllCategory();
    CategoryDTO getCategoryById(Long id);
    void deleteCategory(Long id);
}
