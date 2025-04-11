package com.springcrudengine.product_api;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.springcrudengine.product_api.controller.ProductController;
import com.springcrudengine.product_api.exceptions.GlobalExceptionHandler;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import com.springcrudengine.product_api.model.Product;
import com.springcrudengine.product_api.service.ProductService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.UUID;


public class ProductControllerTest {

    private MockMvc mockMvc;

    @Mock
    private ProductService productService;

    @InjectMocks
    private ProductController productController;

    private final ObjectMapper objectMapper = new ObjectMapper();

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.
                standaloneSetup(productController)
                .setControllerAdvice(new GlobalExceptionHandler())
                .build();  // Set up MockMvc
    }

    @Test
    void testCreateProduct() throws Exception {
        Product request = new Product(null, "Mouse", "Wireless mouse", 20.0, true);
        Product response = new Product(UUID.randomUUID(), "Mouse", "Wireless mouse", 20.0, true);

        // Mock service layer behavior
        Mockito.when(productService.createProduct(any(Product.class))).thenReturn(response);

       mockMvc.perform(post("/api/products")
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(response.getId().toString()))
                .andExpect(jsonPath("$.name").value("Mouse"));
    }


    @Test
    void testGetProductById_NotFound() throws Exception {
        UUID nonExistingId = UUID.randomUUID();

        // When trying to get a product with a non-existing ID, expect 404 NOT FOUND
        when(productService.getProduct(nonExistingId)).thenReturn(java.util.Optional.empty());

        mockMvc.perform(get("/api/products/{id}", nonExistingId))
                .andExpect(status().isNotFound())  // Expecting 404 status
                .andExpect(content().string("Product not found"));  // Expecting the exception message
    }

    @Test
    void testGetProductById_Found() throws Exception {
        UUID id = UUID.randomUUID();
        Product product = new Product(id, "Keyboard", "Mechanical keyboard", 50.0, true);

        Mockito.when(productService.getProduct(id)).thenReturn(Optional.of(product));

        mockMvc.perform(get("/api/products/" + id))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(id.toString()))
                .andExpect(jsonPath("$.name").value("Keyboard"));
    }


    @Test
    void testGetAllProducts() throws Exception {
        List<Product> products = Arrays.asList(
                new Product(UUID.randomUUID(), "Item1", "Desc1", 100.0, true),
                new Product(UUID.randomUUID(), "Item2", "Desc2", 200.0, false)
        );

        Mockito.when(productService.getAllProducts()).thenReturn(products);

        mockMvc.perform(get("/api/products"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2));
    }

    @Test
    void testDeleteProduct() throws Exception {
        UUID id = UUID.randomUUID();

        Mockito.doNothing().when(productService).deleteProduct(id);

        mockMvc.perform(delete("/api/products/" + id))
                .andExpect(status().isOk())
                .andExpect(content().string("Product deleted successfully"));
    }

    @Test
    void testUpdateProduct() throws Exception {
        UUID id = UUID.randomUUID();
        Product request = new Product(null, "Updated", "Updated description", 75.0, false);
        Product updated = new Product(id, "Updated", "Updated description", 75.0, false);

        Mockito.when(productService.updateProduct(eq(id), any(Product.class))).thenReturn(updated);

        mockMvc.perform(put("/api/products/" + id)
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(id.toString()))
                .andExpect(jsonPath("$.name").value("Updated"));
    }
}
