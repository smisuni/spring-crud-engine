package com.springcrudengine.product_api.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.UUID;
import io.swagger.v3.oas.annotations.media.Schema;

/**
 * DTO used for exposing product data via API.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductDTO {

    @Schema(hidden = true)
    private UUID id;

    @Schema(description = "Name of the product", example = "Phone")
    private String name;

    @Schema(description = "Description of the product", example = "Samsung A52")
    private String description;

    @Schema(description = "Cost of the product", example = "300")
    private Double price;

    @Schema(description = "Availability of the product", example = "true")
    private Boolean available;
}
