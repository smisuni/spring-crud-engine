package com.springcrudengine.product_api.mapper;

import com.springcrudengine.product_api.dto.ProductDTO;
import com.springcrudengine.product_api.model.Product;
import org.mapstruct.Mapper;
import java.util.List;


@Mapper(componentModel = "spring")
public interface ProductMapper {

    ProductDTO toDto(Product product);
    Product toEntity(ProductDTO dto);

    List<ProductDTO> toDto(List<Product> products);
}
