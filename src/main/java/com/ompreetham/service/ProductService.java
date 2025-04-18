package com.ompreetham.service;

import com.ompreetham.dto.ProductDTO;
import org.springframework.data.domain.Page;

public interface ProductService {
    ProductDTO createProduct(ProductDTO productDTO);
    ProductDTO getProductById(Long id);
    ProductDTO updateProduct(Long id, ProductDTO productDTO);
    void deleteProduct(Long id);
    Page<ProductDTO> getAllProducts(int page, int size, String sortBy, String direction);
    Page<ProductDTO> searchProductsByName(String name, int page, int size, String sortBy, String direction);
} 