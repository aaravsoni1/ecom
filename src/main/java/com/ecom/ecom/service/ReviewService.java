package com.ecom.ecom.service;

import com.ecom.ecom.entity.User;
import com.ecom.ecom.payload.ReviewDto;
import org.springframework.web.multipart.MultipartFile;

import javax.management.BadAttributeValueExpException;

public interface ReviewService {
    public ReviewDto addReview(ReviewDto review, Long product_id, User user, MultipartFile file);
    public ReviewDto getReviewById(Long dto);
    public ReviewDto updateReview(ReviewDto review);
    public boolean deleteReview(Long reviewId);
    public ReviewDto getAllReviews(Long productId);
    public ReviewDto verifyUser(User user, Long product_id);
}
