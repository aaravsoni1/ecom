package com.ecom.ecom.service;

import com.ecom.ecom.entity.Review;
import com.ecom.ecom.payload.ReviewDto;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.multipart.MultipartFile;

import javax.management.BadAttributeValueExpException;
import java.util.List;

public interface ReviewService {
    public ReviewDto addReview(ReviewDto review, Long product_id, UserDetails user, MultipartFile [] files);
    public ReviewDto getReviewById(Long dto);
    public ReviewDto updateReview(Long reviewid ,ReviewDto review);
    public boolean deleteReview(Long reviewId);
    public List<ReviewDto> getAllReviews(Long productId);
    public Review verifyUser(UserDetails user, Long product_id);
}
