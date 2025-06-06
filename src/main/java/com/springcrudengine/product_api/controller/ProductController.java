package com.springcrudengine.product_api.controller;

import com.springcrudengine.product_api.dto.ProductDTO;
import com.springcrudengine.product_api.exceptions.ApiSuccessResponse;
import com.springcrudengine.product_api.exceptions.ProductNotFoundException;
import com.springcrudengine.product_api.mapper.ProductMapper;
import com.springcrudengine.product_api.service.ProductService;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.Operation;
import java.util.List;
import java.util.UUID;
import jakarta.validation.Valid;

/**
 * REST controller for managing products.
 * Provides endpoints for creating, retrieving, updating, and deleting products.
 */
@RestController
@Tag(name = "Product", description = "Operations related to products")
@RequestMapping("/api/products")
public class ProductController {

    private final ProductService productService;

    public ProductController(ProductService productService, ProductMapper productMapper) {
        this.productService = productService;
    }

    @PostMapping
    @Operation(summary = "Add new products", description = "Create new products inside the database")
    public ResponseEntity<ProductDTO> createProduct(@Valid @RequestBody ProductDTO productDTO) {
        ProductDTO createdProductDTO = productService.createProduct(productDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdProductDTO);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get product info", description = "Display product information from id")
    public ResponseEntity<ProductDTO> getProduct(@PathVariable UUID id) {
        return productService.getProduct(id) // Assuming this returns Optional<ProductDTO>
                .map(ResponseEntity::ok) // Wrap it in ResponseEntity directly
                .orElseThrow(() -> new ProductNotFoundException("Product not found"));
    }

    @GetMapping
    @Operation(summary = "List products", description = "Display all the available products")
    public ResponseEntity<List<ProductDTO>> getAllProducts() {
        List<ProductDTO> productDTOs = productService.getAllProducts();
        return ResponseEntity.ok(productDTOs);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete product", description = "Remove product based on its id")
    @ApiResponse(
            responseCode = "200",
            description = "Product deleted successfully",
            content = @Content(schema = @Schema(hidden = true))
    )
    public ResponseEntity<ApiSuccessResponse> deleteProduct(@PathVariable UUID id) {
        productService.deleteProduct(id);
        ApiSuccessResponse response = new ApiSuccessResponse(
                HttpStatus.OK.value(),
                "Product deleted successfully"
        );
        return ResponseEntity.ok(response);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update product", description = "Change product information based on its id")
    public ResponseEntity<ProductDTO> updateProduct(@PathVariable UUID id, @Valid @RequestBody ProductDTO productDTO) {
        ProductDTO updatedProductDTO = productService.updateProduct(id, productDTO);
        return ResponseEntity.ok(updatedProductDTO);
    }
}
