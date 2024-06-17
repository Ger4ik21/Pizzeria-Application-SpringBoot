package com.modsen.pizzeria.mappers;

import com.modsen.pizzeria.domain.Product;
import com.modsen.pizzeria.dto.response.ProductResponse;
import com.modsen.pizzeria.dto.request.CreateProductRequest;
import com.modsen.pizzeria.dto.request.UpdateProductRequest;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ProductMapper {

    ProductResponse toProductResponse(Product product);

    Product toProduct(ProductResponse productResponse);
    Product toProduct(CreateProductRequest createProductRequest);
    Product toProduct(UpdateProductRequest updateProductRequest);

}
