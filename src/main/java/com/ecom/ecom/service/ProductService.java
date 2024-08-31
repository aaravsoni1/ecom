package com.ecom.ecom.service;

import com.ecom.ecom.payload.ProductDto;

public interface ProductService {
    ProductDto addProduct(ProductDto product);
    ProductDto updateProduct(ProductDto product);
    void deleteProduct(Long productId);
    ProductDto getProductById(Long productId);
}
