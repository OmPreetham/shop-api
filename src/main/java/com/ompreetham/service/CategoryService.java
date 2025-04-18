package com.ompreetham.service;

import com.ompreetham.dto.CategoryDTO;

import java.util.List;

public interface CategoryService {
    CategoryDTO createCategory(CategoryDTO categoryDTO);
    List<CategoryDTO> getAllCategories();
    void deleteCategory(Long id);
} 