package com.ompreetham.mapper;

import com.ompreetham.dto.ProductDTO;
import com.ompreetham.entity.Category;
import com.ompreetham.entity.Product;
import com.ompreetham.exception.ResourceNotFoundException;
import com.ompreetham.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class ProductMapper {

    private final CategoryRepository categoryRepository;

    @Autowired
    public ProductMapper(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    public ProductDTO toDTO(Product product) {
        if (product == null) {
            return null;
        }
        
        Category category = product.getCategory();
        
        return new ProductDTO(
            product.getId(),
            product.getName(),
            product.getDescription(),
            product.getPrice(),
            product.getStockQuantity(),
            category != null ? category.getId() : null,
            category != null ? category.getName() : null,
            product.getCreatedAt(),
            product.getUpdatedAt()
        );
    }
    
    public Product toEntity(ProductDTO productDTO) {
        if (productDTO == null) {
            return null;
        }
        
        Product product = new Product();
        product.setId(productDTO.getId());
        product.setName(productDTO.getName());
        product.setDescription(productDTO.getDescription());
        product.setPrice(productDTO.getPrice());
        product.setStockQuantity(productDTO.getStockQuantity());
        
        // Set category if categoryId is provided
        if (productDTO.getCategoryId() != null) {
            Category category = categoryRepository.findById(productDTO.getCategoryId())
                    .orElseThrow(() -> new ResourceNotFoundException(
                            "Category", "id", productDTO.getCategoryId()));
            
            product.setCategory(category);
        }
        
        // Set timestamps if creating new product
        if (product.getCreatedAt() == null) {
            product.setCreatedAt(LocalDateTime.now());
        }
        
        product.setUpdatedAt(LocalDateTime.now());
        
        return product;
    }
    
    public void updateProductFromDTO(ProductDTO productDTO, Product product) {
        if (productDTO == null) {
            return;
        }
        
        if (productDTO.getName() != null) {
            product.setName(productDTO.getName());
        }
        
        if (productDTO.getDescription() != null) {
            product.setDescription(productDTO.getDescription());
        }
        
        if (productDTO.getPrice() != null) {
            product.setPrice(productDTO.getPrice());
        }
        
        if (productDTO.getStockQuantity() != null) {
            product.setStockQuantity(productDTO.getStockQuantity());
        }
        
        // Update category if categoryId is provided
        if (productDTO.getCategoryId() != null) {
            Category category = categoryRepository.findById(productDTO.getCategoryId())
                    .orElseThrow(() -> new ResourceNotFoundException(
                            "Category", "id", productDTO.getCategoryId()));
            
            product.setCategory(category);
        }
        
        product.setUpdatedAt(LocalDateTime.now());
    }
} 