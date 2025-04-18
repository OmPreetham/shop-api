package com.ompreetham.service.impl;

import com.ompreetham.dto.ProductDTO;
import com.ompreetham.entity.Product;
import com.ompreetham.exception.ResourceNotFoundException;
import com.ompreetham.mapper.ProductMapper;
import com.ompreetham.repository.ProductRepository;
import com.ompreetham.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;
    private final ProductMapper productMapper;

    @Autowired
    public ProductServiceImpl(ProductRepository productRepository, ProductMapper productMapper) {
        this.productRepository = productRepository;
        this.productMapper = productMapper;
    }

    @Override
    public ProductDTO createProduct(ProductDTO productDTO) {
        Product product = productMapper.toEntity(productDTO);
        product.setCreatedAt(LocalDateTime.now());
        product.setUpdatedAt(LocalDateTime.now());

        Product savedProduct = productRepository.save(product);
        return productMapper.toDTO(savedProduct);
    }

    @Override
    public ProductDTO getProductById(Long id) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Product", "id", id));
        
        return productMapper.toDTO(product);
    }

    @Override
    public ProductDTO updateProduct(Long id, ProductDTO productDTO) {
        Product existingProduct = productRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Product", "id", id));
        
        productMapper.updateProductFromDTO(productDTO, existingProduct);
        
        Product updatedProduct = productRepository.save(existingProduct);
        return productMapper.toDTO(updatedProduct);
    }

    @Override
    public void deleteProduct(Long id) {
        if (!productRepository.existsById(id)) {
            throw new ResourceNotFoundException("Product", "id", id);
        }
        productRepository.deleteById(id);
    }

    @Override
    public Page<ProductDTO> getAllProducts(int page, int size, String sortBy, String direction) {
        // Create pageable object with sorting
        Sort sort = createSort(sortBy, direction);
        Pageable pageable = PageRequest.of(page, size, sort);
        
        // Get the page of products
        Page<Product> productPage = productRepository.findAll(pageable);
        
        // Map products to DTOs
        return productPage.map(productMapper::toDTO);
    }

    @Override
    public Page<ProductDTO> searchProductsByName(String name, int page, int size, String sortBy, String direction) {
        // Create pageable object with sorting
        Sort sort = createSort(sortBy, direction);
        Pageable pageable = PageRequest.of(page, size, sort);
        
        // Search for products by name
        Page<Product> productPage = productRepository.findByNameContainingIgnoreCase(name, pageable);
        
        // Map products to DTOs
        return productPage.map(productMapper::toDTO);
    }
    
    // Helper method to create Sort object
    private Sort createSort(String sortBy, String direction) {
        // Default to sorting by id if no valid sort field is provided
        if (sortBy == null || sortBy.isEmpty()) {
            sortBy = "id";
        }
        
        // Create Sort object based on direction
        if (direction != null && direction.equalsIgnoreCase("desc")) {
            return Sort.by(Sort.Direction.DESC, sortBy);
        } else {
            return Sort.by(Sort.Direction.ASC, sortBy);
        }
    }
} 