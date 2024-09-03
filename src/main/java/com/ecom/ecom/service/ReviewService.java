package com.ecom.ecom.service;

import com.ecom.ecom.payload.ReviewDto;

public interface ReviewService {
    public ReviewDto addReview(ReviewDto review);
    public ReviewDto getReviewById(Long dto);
    public ReviewDto updateReview(ReviewDto review);
    public boolean deleteReview(Long reviewId);
    public ReviewDto getAllReviews();
}
