package com.ecom.ecom.controller;

import com.ecom.ecom.exception.ResourceNotFoundException;
import com.ecom.ecom.payload.ProductDto;
import com.ecom.ecom.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping(value = "/api/product", consumes = {"multipart/form-data", "application/octet-stream"})
public class ProductController {
    @Autowired
    private ProductService productService;
    @PostMapping("/add")
    public ResponseEntity<?> createProduct(@ModelAttribute ProductDto dto,
                                           @RequestParam("file") MultipartFile[] files){
        ProductDto createdProduct = productService.addProduct(dto, files);
        return new ResponseEntity<>(createdProduct,HttpStatus.CREATED);
    }

    @PutMapping(value = "/update/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> updateProduct(@PathVariable Long id , @RequestBody ProductDto dto){
        ProductDto updatedProduct = productService.updateProduct(dto, id);
        if(updatedProduct == null){
            throw new ResourceNotFoundException("Product Not Found");
        }
        return new ResponseEntity<>(updatedProduct, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getProductById(@PathVariable Long id){
        try{
            ProductDto productDto = productService.getProductById(id);
            return new ResponseEntity<>(productDto, HttpStatus.OK);
        }catch (ResourceNotFoundException e){
            throw new ResourceNotFoundException("Product Not Found");
        }
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteProduct(@PathVariable Long id){
        try{
            productService.deleteProduct(id);
            return new ResponseEntity<>("Product deleted successfully", HttpStatus.OK);
        }catch (ResourceNotFoundException e){
            throw new ResourceNotFoundException("Product Not Found");
        }
    }
    @GetMapping("/allProducts")
    public ResponseEntity<?> getAllProducts(){
        return new ResponseEntity<>(productService.getAllProduct(), HttpStatus.OK);
    }
}
