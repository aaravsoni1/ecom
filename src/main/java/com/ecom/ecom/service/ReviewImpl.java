package com.ecom.ecom.service;

import com.ecom.ecom.entity.Review;
import com.ecom.ecom.payload.ReviewDto;
import com.ecom.ecom.repository.ReviewRepository;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class ReviewImpl implements ReviewService{

    private ReviewRepository reviewRepository;

    public ReviewImpl(ReviewRepository reviewRepository) {
        this.reviewRepository = reviewRepository;
    }

    @Override
    public ReviewDto addReview(ReviewDto review) {
        Review entity = DtoToEntity(review);
        Review saved = reviewRepository.save(entity);
        return EntityToDto(saved);
    }

    private ReviewDto EntityToDto(Review entity) {
        ReviewDto dto = new ReviewDto();
        dto.setId(entity.getId());
        dto.setComment(entity.getComment());
        dto.setRating(entity.getRating());
        dto.setImage_url(entity.getImage_url());
        dto.setCreated_at(entity.getCreated_at());
        return dto;
    }

    public Review DtoToEntity(ReviewDto dto){
        Review entity = new Review();
        entity.setId(dto.getId());
        entity.setComment(dto.getComment());
        entity.setRating(dto.getRating());
        entity.setImage_url(dto.getImage_url());
        entity.setCreated_at(new Date());
        return entity;
    }

    @Override
    public ReviewDto getReviewById(Long reviewId) {
        Review entity = reviewRepository.findById(reviewId).orElse(null);
        if (entity!= null) {
            return EntityToDto(entity);
        }
        return null;
    }

    @Override
    public ReviewDto updateReview(ReviewDto dto) {
        Review review = reviewRepository.findById(dto.getId()).orElse(null);
        if(review != null){
            review.setUpdated_at(new Date());
            ReviewDto reviewDto = EntityToDto(review);
            reviewRepository.save(review);
            return reviewDto;
        }
        return null;
    }

    @Override
    public boolean deleteReview(Long reviewId) {
        boolean b = reviewRepository.existsById(reviewId);
        if(b){
            reviewRepository.deleteById(reviewId);
            return true;
        }
        return false;
    }

    @Override
    public ReviewDto getAllReviews() {
        Iterable<Review> all = reviewRepository.findAll();
        while(all.iterator().hasNext()) {
            ReviewDto dto = EntityToDto(all.iterator().next());
            return dto;
        }
        return null;
    }
}
