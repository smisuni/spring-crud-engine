package com.springcrudengine.product_api.service;

import com.springcrudengine.product_api.dto.ProductDTO;
import com.springcrudengine.product_api.exceptions.ProductNotFoundException;
import com.springcrudengine.product_api.mapper.ProductMapper;
import com.springcrudengine.product_api.model.Product;
import org.springframework.stereotype.Service;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Service layer for handling business logic related to products.
 * Manages in-memory storage and data transformation between DTOs and entities.
 */
@Service
public class ProductService {

    private final Map<UUID, Product> productRepository = new ConcurrentHashMap<>();
    private final ProductMapper mapper;

    public ProductService(ProductMapper mapper) {
        this.mapper = mapper;
    }

    public ProductDTO createProduct(ProductDTO dto) {
        Product product = mapper.toEntity(dto);
        product.setId(UUID.randomUUID());
        productRepository.put(product.getId(), product);
        return mapper.toDto(product);
    }

    public Optional<ProductDTO> getProduct(UUID id) {
        return Optional.ofNullable(productRepository.get(id))
                .map(mapper::toDto);
    }

    public List<ProductDTO> getAllProducts() {
        return mapper.toDto(new ArrayList<>(productRepository.values()));
    }

    public ProductDTO updateProduct(UUID id, ProductDTO dto) {
        if (!productRepository.containsKey(id)) {
            throw new RuntimeException("Product not found");
        }
        Product product = mapper.toEntity(dto);
        product.setId(id);
        productRepository.put(id, product);
        return mapper.toDto(product);
    }

    public void deleteProduct(UUID id) {
        if (productRepository.containsKey(id)) {
            productRepository.remove(id);
        } else {
            throw new ProductNotFoundException("Product not found");
        }
    }
}
