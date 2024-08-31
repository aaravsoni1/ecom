package com.ecom.ecom.controller;

import com.ecom.ecom.payload.ProductDto;
import com.ecom.ecom.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/product")
public class ProductController {
    @Autowired
    private ProductService productService;
    @PostMapping("/add")
    public ResponseEntity<?> createProduct(@RequestBody ProductDto dto){
        ProductDto createdProduct = productService.addProduct(dto);
        return new ResponseEntity<>(createdProduct,HttpStatus.CREATED);
    }
}
