package com.springcrudengine.product_api;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.springcrudengine.product_api.controller.ProductController;
import com.springcrudengine.product_api.dto.ProductDTO;
import com.springcrudengine.product_api.exceptions.GlobalExceptionHandler;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import com.springcrudengine.product_api.mapper.ProductMapper;
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
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import static org.hamcrest.Matchers.containsString;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class ProductControllerTest {

    private MockMvc mockMvc;

    @Mock
    private ProductService productService;

    @Mock
    private ProductMapper productMapper;

    @InjectMocks
    private ProductController productController;

    private final ObjectMapper objectMapper = new ObjectMapper();

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.
                standaloneSetup(productController)
                .setControllerAdvice(new GlobalExceptionHandler())
                .build();
    }

    @Test
    void testCreateProduct() throws Exception {
        ProductDTO requestDTO = new ProductDTO(null, "IT-Care Mouse", "Wireless mouse", 20.0, true);
        ProductDTO responseDTO = new ProductDTO(UUID.randomUUID(), "IT-Care Mouse", "Wireless mouse", 20.0, true);

        // Mock service layer behavior
        Mockito.when(productService.createProduct(any(ProductDTO.class))).thenReturn(responseDTO);

       mockMvc.perform(post("/api/products")
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(requestDTO)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(responseDTO.getId().toString()))
                .andExpect(jsonPath("$.name").value("IT-Care Mouse"));
    }


    @Test
    void testGetProductById_NotFound() throws Exception {
        UUID nonExistingId = UUID.randomUUID();

        // When trying to get a product with a non-existing ID, expect 404 NOT FOUND
        when(productService.getProduct(nonExistingId)).thenReturn(java.util.Optional.empty());

        mockMvc.perform(get("/api/products/{id}", nonExistingId))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.status").value(404))
                .andExpect(jsonPath("$.message").value("Product not found"));
    }

    @Test
    void testGetProductById_Found() throws Exception {
        UUID id = UUID.randomUUID();
        ProductDTO productDTO = new ProductDTO(id, "Keyboard", "Mechanical keyboard", 50.0, true);

        Mockito.when(productService.getProduct(id)).thenReturn(Optional.of(productDTO));

        mockMvc.perform(get("/api/products/" + id))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(id.toString()))
                .andExpect(jsonPath("$.name").value("Keyboard"));
    }

    @Test
    void testGetAllProducts() throws Exception {
        List<ProductDTO> productsDTO = Arrays.asList(
                new ProductDTO(UUID.randomUUID(), "Item1", "Desc1", 100.0, true),
                new ProductDTO(UUID.randomUUID(), "Item2", "Desc2", 200.0, false)
        );

        Mockito.when(productService.getAllProducts()).thenReturn(productsDTO);

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
                .andExpect(jsonPath("$.message").value("Product deleted successfully"))
                .andExpect(jsonPath("$.status").value(200));
    }

    @Test
    void testUpdateProduct() throws Exception {
        UUID id = UUID.randomUUID();
        ProductDTO requestDTO = new ProductDTO(null, "IT-Care Updated", "Updated description", 75.0, false);
        ProductDTO updatedDTO = new ProductDTO(id, "IT-Care Updated", "Updated description", 75.0, false);

        Mockito.when(productService.updateProduct(eq(id), any(ProductDTO.class))).thenReturn(updatedDTO);

        mockMvc.perform(put("/api/products/" + id)
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(requestDTO)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(id.toString()))
                .andExpect(jsonPath("$.name").value("IT-Care Updated"));
    }

    @Test
    void testValidateProductDTO_InvalidName() throws Exception {
        // Expecting 400 Bad Request with a message for missing or invalid name
        ProductDTO productDTO = new ProductDTO(null, "", "Description", 100.0, true);

        mockMvc.perform(post("/api/products")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(productDTO)))
                        .andExpect(status().isBadRequest())
                        .andExpect(jsonPath("$.status").value(400))
                        .andExpect(jsonPath("$.message",containsString("name")));

    }

    @Test
    void testValidateProductDTO_InvalidPrice() throws Exception {
        // Expecting 400 Bad Request with a message about invalid price
        ProductDTO productDTO = new ProductDTO(null, "IT-Care Laptop", "A powerful laptop", -150.0, true);

        mockMvc.perform(post("/api/products")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(productDTO)))
                        .andExpect(status().isBadRequest())
                        .andExpect(jsonPath("$.status").value(400))
                        .andExpect(jsonPath("$.message").value("price: Price must be at least 0"));
    }

    @Test
    void testValidateProductDTO_InvalidAvailability() throws Exception {

        // Expecting 400 Bad Request with a message about null availability
        ProductDTO productDTO = new ProductDTO(null, "IT-Care Smartphone", "Latest model smartphone", 799.99, null);

        mockMvc.perform(post("/api/products")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(productDTO)))
                        .andExpect(status().isBadRequest())
                        .andExpect(jsonPath("$.status").value(400))
                        .andExpect(jsonPath("$.message").value("available: Availability status must be provided"));
    }

    @Test
    void testGlobalExceptionHandler_InternalServerError() throws Exception {
        // Force an unhandled exception during service call
        Mockito.when(productService.getAllProducts()).thenThrow(new RuntimeException("Something went wrong"));

        mockMvc.perform(get("/api/products"))
                .andExpect(status().isInternalServerError())
                .andExpect(jsonPath("$.status").value(500))
                .andExpect(jsonPath("$.message").value("Internal server error: Something went wrong"));
    }
}
