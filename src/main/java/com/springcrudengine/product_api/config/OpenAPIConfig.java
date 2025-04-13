package com.springcrudengine.product_api.config;

import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import org.springframework.context.annotation.Configuration;

@Configuration
@OpenAPIDefinition(
        info = @Info(
                title = "Product API",
                version = "1.0",
                description = "API for managing products"
        )
)
public class OpenAPIConfig {
}

