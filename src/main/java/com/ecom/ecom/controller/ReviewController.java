package com.ecom.ecom.controller;

import com.ecom.ecom.entity.User;
import com.ecom.ecom.payload.ReviewDto;
import com.ecom.ecom.repository.UserRepository;
import com.ecom.ecom.service.ReviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.management.BadAttributeValueExpException;
import java.util.List;

@RestController
@RequestMapping(value = "/api/review",  consumes = {"multipart/form-data", "application/octet-stream"})
public class ReviewController {

    @Autowired
    private ReviewService reviewService;

    @Autowired
    private UserRepository userRepo;

    @PostMapping(value = "/add", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> createReview(@AuthenticationPrincipal UserDetails userDetails,
                                          @ModelAttribute ReviewDto dto,
                                          @RequestParam("file") MultipartFile[] files,
                                          @RequestParam Long productId) {
        // Debugging log to check if the ReviewDto is populated correctly
        System.out.println("Review DTO: " + dto);

        // Verify if the user has already posted a review
        if (reviewService.verifyUser(userDetails, productId) == null) {
            // Add review
            ReviewDto added = reviewService.addReview(dto, productId, userDetails, files);

            // Debugging log to check if the returned DTO is correct
            System.out.println("Added Review DTO: " + added);

            return new ResponseEntity<>(added, HttpStatus.CREATED);
        } else {
            return new ResponseEntity<>("User has already posted a review.", HttpStatus.CONFLICT);
        }
    }


    @PutMapping(value = "/update/{reviewid}", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> updateReview(@PathVariable Long reviewid , @RequestBody ReviewDto dto){
        ReviewDto updated = reviewService.updateReview(reviewid, dto);
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
        List<ReviewDto> allReviews = reviewService.getAllReviews(productId);
        if(allReviews == null){
            return new ResponseEntity<>("No Reviews Found", HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(allReviews, HttpStatus.OK);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteReview(@PathVariable Long id){
        boolean deleted = reviewService.deleteReview(id);
        if(!deleted){
            return new ResponseEntity<>("Review Not Found", HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>("Review deleted successfully", HttpStatus.OK);
    }
}
