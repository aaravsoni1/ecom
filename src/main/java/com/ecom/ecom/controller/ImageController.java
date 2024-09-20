package com.ecom.ecom.controller;

import com.ecom.ecom.service.ProductImageService;
import com.ecom.ecom.service.ReviewImageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/bucket")
public class ImageController {
    @Autowired
    private ProductImageService productImageService;

    @Autowired
    private ReviewImageService reviewImageService;

    @PostMapping("/upload/productImage")
    public ResponseEntity<?> uploadProductFile(@RequestParam MultipartFile[] file) {
        return new ResponseEntity<>(productImageService.uploadImage(file), HttpStatus.OK);
    }

    @PostMapping("/upload/reviewImage/{bucketName}")
    public ResponseEntity<?> uploadReviewFile(@RequestParam MultipartFile[] file) {
        return new ResponseEntity<>(reviewImageService.uploadReviewImages(file), HttpStatus.OK);
    }

    @DeleteMapping("/delete/productImage")
    public ResponseEntity<?> deleteProductImage(@RequestParam String imageUrl) {
        productImageService.deleteProductImage(imageUrl);
        return new ResponseEntity<>("Product Image deleted successfully", HttpStatus.OK);
    }
}
