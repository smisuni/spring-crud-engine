package com.springcrudengine.product_api;

import com.springcrudengine.product_api.dto.ProductDTO;
import com.springcrudengine.product_api.service.ProductService;
import org.junit.jupiter.api.Test;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import static org.junit.jupiter.api.Assertions.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@ActiveProfiles("test")
@Transactional
public class ProductServiceTest {

    @Autowired
    private ProductService productService;

    private ProductDTO createDTO(String name, String desc, double price, boolean available) {
        return new ProductDTO(null, name, desc, price, available);
    }

    @Test
    void testCreateProduct() {
        ProductDTO dto = new ProductDTO(null, "Laptop", "Gaming laptop", 1200.0, true);
        ProductDTO created = productService.createProduct(dto);

        assertNotNull(created.getId());
        assertEquals("Laptop", created.getName());
        assertEquals("Gaming laptop", created.getDescription());
        assertEquals(1200.0, created.getPrice());
        assertTrue(created.getAvailable());
    }

    @Test
    void testGetProductWhenExists() {
        ProductDTO dto = new ProductDTO(null, "Mouse", "Wireless mouse", 30.0, true);
        ProductDTO created = productService.createProduct(dto);
        Optional<ProductDTO> found = productService.getProduct(created.getId());

        assertTrue(found.isPresent());
        assertEquals("Mouse", found.get().getName());
    }

    @Test
    void testGetProductWhenNotExists() {
        UUID randomId = UUID.randomUUID();
        Optional<ProductDTO> product = productService.getProduct(randomId);

        assertFalse(product.isPresent());
    }

    @Test
    void testGetAllProductsEmptyInitially() {
        List<ProductDTO> products = productService.getAllProducts();

        assertTrue(products.isEmpty());
    }

    @Test
    void testGetAllProductsAfterAdding() {
        productService.createProduct(createDTO("Monitor", "4K Monitor", 350.0, true));
        productService.createProduct(createDTO("Keyboard", "Mechanical keyboard", 100.0, true));
        List<ProductDTO> products = productService.getAllProducts();

        assertEquals(2, products.size());
    }

    @Test
    void testUpdateProductWhenExists() {
        ProductDTO original = productService.createProduct(createDTO("Tablet", "Old tablet", 200.0, true));
        UUID id = original.getId();
        ProductDTO updated = createDTO("Tablet Pro", "New tablet version", 300.0, false);
        ProductDTO result = productService.updateProduct(id, updated);

        assertEquals(id, result.getId());
        assertEquals("Tablet Pro", result.getName());
        assertEquals("New tablet version", result.getDescription());
        assertEquals(300.0, result.getPrice());
        assertFalse(result.getAvailable());
    }

    @Test
    void testUpdateProductWhenNotExists() {
        UUID nonExistentId = UUID.randomUUID();
        ProductDTO updated = createDTO("Camera", "DSLR", 800.0, true);

        Exception exception = assertThrows(RuntimeException.class, () -> {
            productService.updateProduct(nonExistentId, updated);
        });
        assertEquals("Product not found", exception.getMessage());
    }

    @Test
    void testDeleteProductWhenExists() {
        ProductDTO product = productService.createProduct(createDTO("Printer", "Laser printer", 150.0, true));

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
