package com.ompreetham.mapper;

import com.ompreetham.dto.CategoryDTO;
import com.ompreetham.entity.Category;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class CategoryMapper {

    public CategoryDTO toDTO(Category category) {
        if (category == null) {
            return null;
        }
        
        return new CategoryDTO(
            category.getId(),
            category.getName(),
            category.getDescription(),
            category.getCreatedAt(),
            category.getUpdatedAt()
        );
    }
    
    public Category toEntity(CategoryDTO categoryDTO) {
        if (categoryDTO == null) {
            return null;
        }
        
        Category category = new Category();
        category.setId(categoryDTO.getId());
        category.setName(categoryDTO.getName());
        category.setDescription(categoryDTO.getDescription());
        
        // Set timestamps if creating new category
        if (category.getCreatedAt() == null) {
            category.setCreatedAt(LocalDateTime.now());
        }
        
        category.setUpdatedAt(LocalDateTime.now());
        
        return category;
    }
    
    public void updateCategoryFromDTO(CategoryDTO categoryDTO, Category category) {
        if (categoryDTO == null) {
            return;
        }
        
        if (categoryDTO.getName() != null) {
            category.setName(categoryDTO.getName());
        }
        
        if (categoryDTO.getDescription() != null) {
            category.setDescription(categoryDTO.getDescription());
        }
        
        category.setUpdatedAt(LocalDateTime.now());
    }
} 