package com.ecom.ecom.service;

import com.ecom.ecom.entity.Product;
import com.ecom.ecom.payload.ProductDto;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface ProductService {
    ProductDto addProduct(ProductDto product , MultipartFile [] files);
    ProductDto updateProduct(ProductDto product, Long categoryId);
    void deleteProduct(Long productId);
    ProductDto getProductById(Long productId);
    List<ProductDto> getAllProduct();
}
