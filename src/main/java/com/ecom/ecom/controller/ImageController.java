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

    @PostMapping("/upload/productImage/{bucketName}")
    public ResponseEntity<?> uploadProductFile(@RequestParam MultipartFile file,
                                     @PathVariable String bucketName) {
        return new ResponseEntity<>(productImageService.uploadImage(file, bucketName), HttpStatus.OK);
    }

    @PostMapping("/upload/reviewImage/{bucketName}")
    public ResponseEntity<?> uploadReviewFile(@RequestParam MultipartFile file,
                                                   @PathVariable String bucketName) {
        return new ResponseEntity<>(reviewImageService.uploadReviweImage(file, bucketName), HttpStatus.OK);
    }
}
