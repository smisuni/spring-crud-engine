package com.springcrudengine.product_api.service;

import com.springcrudengine.product_api.model.Product;
import org.springframework.stereotype.Service;


import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;


@Service
public class ProductService {
    private final Map<UUID, Product> productRepository = new ConcurrentHashMap<>();

    public Product createProduct(Product product) {
        product.setId(UUID.randomUUID());
        productRepository.put(product.getId(), product);
        return product;
    }

    public Optional<Product> getProduct(UUID id) {
        return Optional.ofNullable(productRepository.get(id));
    }

    public List<Product> getAllProducts() {
        return new ArrayList<>(productRepository.values());
    }

    public Product updateProduct(UUID id, Product updatedProduct) {
        if (productRepository.containsKey(id)) {
            updatedProduct.setId(id);
            productRepository.put(id, updatedProduct);
            return updatedProduct;
        }
        throw new RuntimeException("Product not found");
    }

    public void deleteProduct(UUID id) {
        if (productRepository.containsKey(id)) {
            productRepository.remove(id);
        } else {
            throw new RuntimeException("Product not found");
        }
    }
}
