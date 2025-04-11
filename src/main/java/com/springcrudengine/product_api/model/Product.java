package com.springcrudengine.product_api.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor

public class Product {
    private UUID id;
    private String name;
    private String description;
    private double price;
    private boolean available;
}
