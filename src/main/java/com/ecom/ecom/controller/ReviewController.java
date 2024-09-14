package com.ecom.ecom.controller;

import com.ecom.ecom.entity.User;
import com.ecom.ecom.payload.ReviewDto;
import com.ecom.ecom.service.ReviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.management.BadAttributeValueExpException;

@RestController
@RequestMapping("/api/review")
public class ReviewController {

    @Autowired
    private ReviewService reviewService;

    @PostMapping("/add")
    public ResponseEntity<?> createReview(@AuthenticationPrincipal User user ,
                                          @RequestBody ReviewDto dto,
                                          @RequestParam Long product_id,
                                          @RequestParam MultipartFile file)
    {
        if(reviewService.verifyUser(user, product_id)==null){
            ReviewDto added = reviewService.addReview(dto, product_id, user, file);
                return new ResponseEntity<>(added, HttpStatus.CREATED);
        }
        else{

            return new ResponseEntity<>("User not authorized to add review for this product", HttpStatus.UNAUTHORIZED);
        }
    }

    @PutMapping("/update")
    public ResponseEntity<?> updateReview(@RequestBody ReviewDto dto){
        ReviewDto updated = reviewService.updateReview(dto);
        if(updated == null){
            return new ResponseEntity<>("Review Not Found", HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(updated, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getReview(@PathVariable Long id){
        ReviewDto reviewDto = reviewService.getReviewById(id);
        if(reviewDto == null){
            return new ResponseEntity<>("Review Not Found", HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(reviewDto, HttpStatus.OK);
    }

    @GetMapping("/product/{productId}")
    public ResponseEntity<?> getAllReviewsByProductId(@PathVariable Long productId){
        return new ResponseEntity<>(reviewService.getAllReviews(productId), HttpStatus.OK);
    }

    @DeleteMapping("/delete")
    public ResponseEntity<?> deleteReview(@PathVariable Long id){
        boolean deleted = reviewService.deleteReview(id);
        if(!deleted){
            return new ResponseEntity<>("Review Not Found", HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>("Review deleted successfully", HttpStatus.OK);
    }
}
