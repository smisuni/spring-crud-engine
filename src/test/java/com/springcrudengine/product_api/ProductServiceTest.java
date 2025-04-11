package com.springcrudengine.product_api;

import com.springcrudengine.product_api.model.Product;
import com.springcrudengine.product_api.service.ProductService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import static org.junit.jupiter.api.Assertions.*;

public class ProductServiceTest {

    private ProductService productService;

    @BeforeEach
    void setUp() {
        productService = new ProductService();
    }

    @Test
    void testCreateProduct() {
        Product product = new Product(null, "Laptop", "Gaming laptop", 1200.0, true);
        Product created = productService.createProduct(product);

        assertNotNull(created.getId());
        assertEquals("Laptop", created.getName());
        assertEquals("Gaming laptop", created.getDescription());
        assertEquals(1200.0, created.getPrice());
        assertTrue(created.isAvailable());
    }

    @Test
    void testGetProductWhenExists() {
        Product product = new Product(null, "Mouse", "Wireless mouse", 30.0, true);
        Product created = productService.createProduct(product);
        Optional<Product> found = productService.getProduct(created.getId());

        assertTrue(found.isPresent());
        assertEquals("Mouse", found.get().getName());
    }

    @Test
    void testGetProductWhenNotExists() {
        UUID randomId = UUID.randomUUID();
        Optional<Product> product = productService.getProduct(randomId);

        assertFalse(product.isPresent());
    }

    @Test
    void testGetAllProductsEmptyInitially() {
        List<Product> products = productService.getAllProducts();

        assertTrue(products.isEmpty());
    }

    @Test
    void testGetAllProductsAfterAdding() {
        productService.createProduct(new Product(null, "Monitor", "4K Monitor", 350.0, true));
        productService.createProduct(new Product(null, "Keyboard", "Mechanical keyboard", 100.0, true));
        List<Product> products = productService.getAllProducts();

        assertEquals(2, products.size());
    }

    @Test
    void testUpdateProductWhenExists() {
        Product original = productService.createProduct(new Product(null, "Tablet", "Old tablet", 200.0, true));
        UUID id = original.getId();
        Product updated = new Product(null, "Tablet Pro", "New tablet version", 300.0, false);
        Product result = productService.updateProduct(id, updated);

        assertEquals(id, result.getId());
        assertEquals("Tablet Pro", result.getName());
        assertEquals("New tablet version", result.getDescription());
        assertEquals(300.0, result.getPrice());
        assertFalse(result.isAvailable());
    }

    @Test
    void testUpdateProductWhenNotExists() {
        UUID nonExistentId = UUID.randomUUID();
        Product updated = new Product(null, "Camera", "DSLR", 800.0, true);

        Exception exception = assertThrows(RuntimeException.class, () -> {
            productService.updateProduct(nonExistentId, updated);
        });
        assertEquals("Product not found", exception.getMessage());
    }

    @Test
    void testDeleteProductWhenExists() {
        Product product = productService.createProduct(new Product(null, "Printer", "Laser printer", 150.0, true));

        assertDoesNotThrow(() -> productService.deleteProduct(product.getId()));
        assertFalse(productService.getProduct(product.getId()).isPresent());
    }

    @Test
    void testDeleteProductWhenNotExists() {
        UUID nonExistentId = UUID.randomUUID();

        Exception exception = assertThrows(RuntimeException.class, () -> {
            productService.deleteProduct(nonExistentId);
        });
        assertEquals("Product not found", exception.getMessage());
    }
}
