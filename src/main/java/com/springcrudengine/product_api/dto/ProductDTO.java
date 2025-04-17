package com.springcrudengine.product_api.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.UUID;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import jakarta.validation.constraints.PositiveOrZero;
import com.springcrudengine.product_api.validation.ContainsITCare;

/**
 * DTO used for exposing product data via API.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductDTO {

    @Schema(hidden = true)
    private UUID id;

    @Schema(description = "Name of the product", example = "IT-Care Product : Product1")
    @ContainsITCare
    @NotBlank(message = "Name cannot be blank")
    @Size(min = 3, message = "Name must have at least 3 characters")
    private String name;

    @Schema(description = "Description of the product", example = "This is the product description")
    private String description;

    @Schema(description = "Cost of the product", example = "300")
    @NotNull(message = "Price cannot be null")
    @PositiveOrZero(message = "Price must be at least 0")
    private Double price;

    @Schema(description = "Availability of the product", example = "true")
    @NotNull(message = "Availability status must be provided")
    private Boolean available;
}
