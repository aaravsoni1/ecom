package com.ecom.ecom.service;

import com.ecom.ecom.entity.Product;
import com.ecom.ecom.payload.ProductDto;

import java.util.List;

public interface ProductService {
    ProductDto addProduct(ProductDto product);
    ProductDto updateProduct(ProductDto product);
    void deleteProduct(Long productId);
    ProductDto getProductById(Long productId);
    List<ProductDto> getAllProduct();
}
