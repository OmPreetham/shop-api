package com.ompreetham.repository;

import com.ompreetham.entity.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
    
    // Find products by name containing the search term (case-insensitive)
    @Query("SELECT p FROM Product p WHERE LOWER(p.name) LIKE LOWER(CONCAT('%', :name, '%'))")
    Page<Product> findByNameContainingIgnoreCase(@Param("name") String name, Pageable pageable);
    
    // Find all products with pagination and sorting
    Page<Product> findAll(Pageable pageable);
    
    // Find products by category
    Page<Product> findByCategoryId(Long categoryId, Pageable pageable);
} 